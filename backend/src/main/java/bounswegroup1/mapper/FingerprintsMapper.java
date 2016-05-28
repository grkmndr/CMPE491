package bounswegroup1.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import bounswegroup1.model.Fingerprint;

public class FingerprintsMapper implements ResultSetMapper<List<Fingerprint>> {
	List<Fingerprint> res;
    Fingerprint curr;
    long lastId;

    public FingerprintsMapper(){
        res = new ArrayList<Fingerprint>();
        lastId = -1;
    }

    @Override
    public List<Fingerprint> map(int idx, ResultSet rs, StatementContext ctx) throws SQLException {
        if (rs.getLong("id") != lastId) {
            curr = new Fingerprint(rs.getLong("id"), rs.getString("song_name"), rs.getString("artist_name"), 
                rs.getString("makam"), rs.getString("fingerprint"));
            lastId = rs.getLong("id");
            res.add(curr);
        }
        return res;
    }
}