package toms.lib.libtcommon;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;

/**
 * Created by toms on 03/01/16.
 * Simple interface for launching, stopping and sending message to a simple service (derived from TService)
 */
public class TIService {

	// Calling context
	protected Context mContext;

	public TIService(Context c, Class<?>cls)
	{
		mContext = c;
		mCls = cls;
		mServiceIntent = new Intent(mContext, mCls);
	}

	// Service binder.
	protected TService mBoundService;

	//  Bounding status
	boolean mIsBound = false;

	// for stopping the service after unbind
	Intent mServiceIntent;

	// service class
	Class<?> mCls;

	private ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			// The service is a thread of our process: the binder can be cast to the service object
			mBoundService = ((TService.LocalBinder)service).getService();
		}

		public void onServiceDisconnected(ComponentName className) {
			// never happens when the service is in the application process
			mBoundService = null;
		}
	};

	// start the service
	public void start() {
		ComponentName name =mContext.startService(mServiceIntent);
		mIsBound = mContext.bindService(mServiceIntent, mConnection, Context.BIND_AUTO_CREATE);
	}

	// ask for stopping the service
	public void stop() {
		if (mIsBound) {
			mContext.unbindService(mConnection);
			mContext.stopService(mServiceIntent);
			mIsBound = false;
		}
	}

	// send a custom message to the service message handler
	public boolean sendMessage(Message Msg)
	{
		if (mBoundService != null)
		{
			return mBoundService.sendMessage(Msg);
		}
		return false;
	}

	public boolean sendMessage(int code)
	{
		if (mBoundService != null)
		{
			return mBoundService.sendMessage(code);
		}
		return false;
	}

	// return bound status
	public boolean isBound()
	{
		return mIsBound;
	}

	public boolean ready()
	{
		if (mBoundService == null)
		{
			return false;
		}
		return mBoundService.ready();
	}

}
