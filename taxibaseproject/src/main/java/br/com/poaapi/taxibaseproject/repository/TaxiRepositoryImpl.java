package br.com.poaapi.taxibaseproject.repository;

import br.com.poaapi.taxibaseproject.integration.TaxiIntegration;
import br.com.poaapi.taxibaseproject.model.Taxi;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

@Repository
@AllArgsConstructor
public class TaxiRepositoryImpl extends JdbcDaoSupport {

    private final TaxiRepository repository;

    @Autowired
    private final DataSource dataSource;

    @PostConstruct
    private void initialize(){
        this.setDataSource(dataSource);
        TaxiIntegration.readFile().forEach(this::save);
    }

    public List<Taxi> findAll(){
        return repository.findAll();
    }

    public Taxi findById(Integer id){
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Taxi> filterByName(String name){
        return repository.filterByName(name);
    }

    public void delete(Taxi taxi){
        repository.delete(taxi);
    }

    public void update(Taxi taxi, String[] params){
        taxi.setName(params[0] == null ? taxi.getLat() : params[0]);
        taxi.setLat(params[1] == null ? taxi.getLat() : params[1]);
        taxi.setLng(params[2] == null ? taxi.getLng() : params[2]);
        final String query = "UPDATE PONTOS_TAXI SET NAME = ?, LAT = ?, LNG = ? WHERE ID = ?";
        final Object[] entries = new Object[]{taxi.getName(), taxi.getLat(), taxi.getLng(), taxi.getId()};
        try {
            this.getJdbcTemplate().update(query, entries);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    public Taxi save(Taxi taxi){
        Taxi stand = this.find(taxi);
        if (stand == null){
            final String query = "INSERT INTO PONTOS_TAXI (NAME, LAT, LNG, REGISTER_TIME) VALUES (?, ?, ?, ?)";
            final Object[] entries = new Object[]{taxi.getName(), taxi.getLat(), taxi.getLng(), taxi.getTime()};
            try {
                this.getJdbcTemplate().update(query, entries);
            } catch (DataAccessException e) {
                e.printStackTrace();
            } finally {
                stand = this.find(taxi);
            }
        }
        taxi.setId(stand.getId());
        taxi.setTime(stand.getTime());
        TaxiIntegration.appendFile(taxi);
        return repository.save(taxi);
    }

    private Taxi find(Taxi taxi){
        final String query = "SELECT * FROM PONTOS_TAXI WHERE NAME = ? AND LAT = ? AND LNG = ?";
        final Object[] entries = new Object[]{taxi.getName(), taxi.getLat(), taxi.getLng()};
        Taxi stand;
        try {
            stand = this.getJdbcTemplate().queryForObject(query, entries, new BeanPropertyRowMapper<>(Taxi.class));
        } catch (EmptyResultDataAccessException e) {
            stand = null;
        }
        return stand;
    }

}
