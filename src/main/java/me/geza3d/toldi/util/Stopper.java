package me.geza3d.toldi.util;

public class Stopper {

private long checkTime = 0;
	
	/**
	 * It'll start a stopper, and return a boolean value if it was successful or not.
	 * NOTE: You must make multiple Stopper objects in order to have multiple stoppers at once! The stopper have to be reseted to use this method!
	 */
	public boolean startStopper() {
		if(checkTime == 0) {
			checkTime = System.currentTimeMillis();
			return true;
		}
		return false;
	}
	
	/**
	 * It'll check if the stopper reached the amount of time you give in @param sec.
	 * 
	 * If you want to reset the stopper set @param reset to true. This way, you can start a new stopper in this object.
	 */
	public boolean checkStopper(int sec, boolean reset) {
		boolean noStopper = checkTime == 0;
		if(!noStopper){	
			boolean check = checkTime + (sec * 1000) < System.currentTimeMillis();
			if(check) {
				if(reset)
					checkTime = 0;
				return true;
			}
		}
		
		return noStopper;
		
	}
	
	/**
	 * It'll check if the stopper reached the amount of time you give in @param milisec.
	 * 
	 * If you want to reset the stopper set @param reset to true. This way, you can start a new stopper in this object.
	 */
	public boolean checkStopper(long milisec, boolean reset) {
		boolean noStopper = checkTime == 0;
		if(!noStopper){	
			boolean check = checkTime + milisec < System.currentTimeMillis();
			if(check) {
				if(reset)
					checkTime = 0;
				return true;
			}
		}
		
		return noStopper;
		
	}
	
	/**
	 * It resets the stopper, so you can start a new stopper in this object.
	 */
	public void resetStopper() {
		checkTime = 0;
	}
	
	/**
	 * Tells you if the stopper has been started or not.
	 */
	public boolean isStopperStarted() {
		return checkTime != 0;
	}
	
	public int getTimePassedSec() {
		return (int) (getTimePassedMilliSec() / 1000);
	}
	
	public long getTimePassedMilliSec() {
		if(checkTime != 0) {
			return System.currentTimeMillis() - checkTime;
		}
		return 0;
	}
	
	public void restartStopper() {
		resetStopper();
		startStopper();
	}
}
