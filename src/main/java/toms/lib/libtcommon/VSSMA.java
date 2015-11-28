package toms.lib.libtcommon;

/**
 * Very Simple Simple Moving Average
 * Created by toms on 29/08/15.
 */
public class VSSMA {

    // SMA samples (cyclic)
    protected float mData[];

    // Actual SMA value
    protected float mMedia;

    // SMA period (number of samples)
    protected int mPeriod;

    // Internal, insert index in mdata
    private int mInsertIndex;

    // Constructor period is mandatory
    public VSSMA(int period)
    {
        // Period can't be lesser than one.
        if (period <= 0)
            period = 1;

        mData = new float[period];
        mPeriod = period;
        mInsertIndex = 0;
        mMedia = 0;
    }

    // Add a sample and return insert index.
    public void Add(float fValue)
    {
        if (mInsertIndex >= mPeriod)
        {
            mInsertIndex = 0;
        }

        float fVal = fValue/mPeriod;
        mMedia = mMedia - mData[mInsertIndex] + fVal;
        mData[mInsertIndex]= fVal;

        mInsertIndex++;
    }

    public int getCurrentIndex()
    {
        return mInsertIndex;
    }

    // SMA value
    public float get()
    {
        return mMedia;
    }
}