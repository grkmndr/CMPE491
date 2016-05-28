package bounswegroup1.db;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import bounswegroup1.mapper.FingerprintsMapper;

import bounswegroup1.model.Fingerprint;

public abstract class FingerprintDAO {
	@GetGeneratedKeys
	@SqlUpdate("insert into fingerprints (song_name, artist_name, makam, fingerprint) values (:songName, :artistName, :makam, :fingerprint)")
	abstract protected Long _addFingerprint(@BindBean Fingerprint fingerprint);

	@SqlQuery("select * from fingerprints"+
	" where id = '1'")
	@Mapper(FingerprintsMapper.class)
	abstract protected List<List<Fingerprint>> _getSongForFingerprint(@Bind("fingerprint") String fingerprint);

	public void addFingerprint(Fingerprint fingerprint){
		Long id = _addFingerprint(fingerprint);
		fingerprint.setId(id);
	}

	public Fingerprint getSongForFingerprint(String fingerprint) {
        List<List<Fingerprint>> res = _getSongForFingerprint(fingerprint);

        if (res == null || res.isEmpty()) {
            return new Fingerprint();
        }

        return res.get(0).get(0);
    }
}
