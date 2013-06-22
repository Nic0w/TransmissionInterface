package fr.team0w.transmission.iface;

import java.util.List;

public interface TorrentBatch {

	public void start(boolean now);
	
	public void stop();
	
	public void verify();
	
	public void reannounce();
	
	public TorrentSetRequest set();
	
	public List<Torrent> get();
	
}
