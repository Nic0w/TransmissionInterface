/**
 * 
 */
package fr.team0w.transmission.iface;

/**
 * @author nic0w
 *
 */
public interface TorrentSetRequest {

	/**
	 * @param bandwithPriority Torrent's bandwidth tr_priority_t
	 * @return
	 */
	public TorrentSetRequest bandwidthPriority(Number bandwithPriority); 
	
	/**
	 * @param maxDownloadSpeed The maximum download speed in KB/
	 * @return
	 */
	public TorrentSetRequest downloadLimit(Number maxDownloadSpeed); 
	
	/**
	 * @param enableDownloadLimit If true, honor download limit
	 * @return
	 */
	public TorrentSetRequest downloadLimited(boolean enableDownloadLimit); 
	
	/**
	 * @param fileIDs Indices of file(s) to download
	 * @return
	 */
	public TorrentSetRequest filesWanted(int ...fileIDs);
	
	/**
	 * @param fileIDs Indices of file(s) to not download
	 * @return
	 */
	public TorrentSetRequest filesUnwanted(int ...fileIDs);
	
	public TorrentSetRequest honorsSessionLimits(boolean bool); //true if session); //upload limits are honored
	
	public TorrentSetRequest ids(Object ...obj); //torrent list, as described in); //3.1
	
	public TorrentSetRequest location(String s); //new location); //of the torrent's content
	
	public TorrentSetRequest peerlimit(Number n); //maximum Number n); //of peers
	
	public TorrentSetRequest priorityhigh(Object ...obj); //indices of highpriority); //file(s)
	
	public TorrentSetRequest prioritylow(Object ...obj); //indices of lowpriority); //file(s)
	
	public TorrentSetRequest prioritynormal(Object ...obj); //indices of normalpriority); //file(s)
	
	public TorrentSetRequest queuePosition(Number n); //position); //of this torrent in); //its queue [0...n)
	
	public TorrentSetRequest seedIdleLimit(Number n); //torrentlevel Number n); //of minutes of seeding); //inactivity); //public TorrentSetRequest seedIdleMode(Number n); //which seeding); //inactivity); //to use.  See tr_idlelimit
	
	public TorrentSetRequest seedRatioLimit(double d);     //torrentlevel seeding); //ratio
	
	public TorrentSetRequest seedRatioMode(Number n); //which ratio to use.  See tr_ratiolimit
	
	public TorrentSetRequest trackerAdd(Object ...obj); //String ss of announce URLs to add
	
	public TorrentSetRequest trackerRemove(Object ...obj); //ids of trackers to remove
	
	public TorrentSetRequest trackerReplace(Object ...obj); //pairs of <trackerId/new announce URLs>
	
	public TorrentSetRequest uploadLimit(Number n); //maximum upload speed (KBps)
	
	public TorrentSetRequest uploadLimited(boolean bool); //true if "uploadLimit" is honored
}
