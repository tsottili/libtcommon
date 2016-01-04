package toms.lib.libtcommon;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;

/**
 * Created by toms on 03/01/16.
 * Implements little infrastructure for managing a simple application service.
 * Just derive a class from this, implement abstract methods and launche the service using a TIService
 */
public abstract class TService extends Service
{
	// Stuff for service management.
	private Looper mServiceLooper;
	private ServiceHandler mServiceHandler;
	protected int mMyStartId = 0;

	protected boolean mbReady = false;

	// This is the object that receives interactions from clients.  See
	// RemoteService for a more complete example.
	private final IBinder mBinder = new LocalBinder();

	// Handler that receives messages from the thread
	private final class ServiceHandler extends Handler {

		// Service reference
		protected TService mCaller;

		public ServiceHandler(TService caller, Looper looper) {
			super(looper);
			mCaller = caller;
		}

		@Override
		public void handleMessage(Message msg) {
			mCaller.handleMessage(msg);
		}
	}

	@Override
	public void onCreate() {
		// Start up the thread running the service.  Note that we create a
		// separate thread because the service normally runs in the process's
		// main thread, which we don't want to block.  We also make it
		// background priority so CPU-intensive work will not disrupt our UI.
		HandlerThread thread = new HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND);

		thread.start();

		// Get the HandlerThread's Looper and use it for our Handler
		mServiceLooper = thread.getLooper();
		mServiceHandler = new ServiceHandler(this, mServiceLooper);

	}

	public class LocalBinder extends Binder {
		TService getService() {
			return TService.this;
		}
	}

	public TService() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		// do I need this?
		mMyStartId = startId;

		// we're ready for receive message
		mbReady = true;

		// restart if necessary
		return START_STICKY;
	}


	@Override
	public void onDestroy() {
		mbReady = false;
	}

	public boolean ready()
	{
		return mbReady;
	}

	// Implement service message handler here
	abstract public void handleMessage(Message msg);

	// Use this method to send message to the service
	public boolean sendMessage(Message msg)
	{
		return mServiceHandler.sendMessage(msg);
	}

	// Use this method to send message to the service
	public boolean sendMessage(int code)
	{
		Message msg = Message.obtain(mServiceHandler, code);
		return mServiceHandler.sendMessage(msg);
	}



}
