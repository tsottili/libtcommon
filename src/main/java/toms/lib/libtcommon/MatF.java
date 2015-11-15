package toms.lib.libtcommon;

/**
 * Created by toms on 24/03/14.
 */
public class MatF {

    protected float mDati[];

    protected int mRows;
    protected int mCols;

    public MatF(int iRows, int iColumns)
    {
        mRows = iRows;
        mCols = iColumns;
        mDati = new float[mRows*mCols];
    }

    public MatF(int iRows, int iColumns, float[] fArray)
    {
        int ilen = fArray.length;
        if ( iRows*iColumns == ilen )
        {
            mRows = iRows;
            mCols = iColumns;
            mDati = new float[mRows*mCols];
            for (int i = 0; i < mRows*mCols; i++)
            {
                mDati[i] = fArray[i];
            }
        }
        else
        {
            mRows = 0;
            mCols = 0;
            mDati = null;
        }
    }

    public MatF(int iRows, int iColumns, Float[] fArray)
    {
        int ilen = fArray.length;
        if ( iRows*iColumns == ilen )
        {
            mRows = iRows;
            mCols = iColumns;
            mDati = new float[mRows*mCols];
            for (int i = 0; i < mRows*mCols; i++)
            {
                mDati[i] = fArray[i];
            }
        }
        else
        {
            mRows = 0;
            mCols = 0;
            mDati = null;
        }
    }

    public MatF(MatF m)
    {
        mRows = m.rows();
        mCols = m.columns();
        for (int i = 0; i < mRows*mCols; i++)
        {
            mDati[i] = m.mDati[i];
        }
    }

    public float get(int i, int j)
    {
        return mDati[i + j*mRows];
    }

    public void set(int i, int j, float fVal)
    {
        mDati[i + j*mRows] = fVal;
    }

    public void zeros()
    {
        for (int i = 0; i < mRows*mCols; i++)
        {
            mDati[i] = 0;
        }
    }

    public void fill(float fVal)
    {
        for (int i = 0; i < mRows*mCols; i++)
        {
            mDati[i] = fVal;
        }
    }

    public int rows()
    {
        return mRows;
    }

    public int columns()
    {
        return mCols;
    }

    public boolean canMultiply(MatF M2)
    {
        return (columns() == M2.rows());
    }

    public MatF multiply(MatF M2)
    {
        if (canMultiply(M2) == false)
        {
            MatF m = new MatF(0,0);
            return m;
        }

        MatF M = new MatF(mRows, M2.columns());

        for (int k = 0; k < mRows*M2.columns(); k++)
        {
            float fVal = 0;

            // i = 0, corrisponde a M[0,0] = M1[0,...] * M2[..., 0]
            int irow = k % M.rows();
            int icol = k / M.rows();

            for (int i = 0; i < mCols; i++)
            {
                fVal = fVal + get(irow, i) * M2.get(i,icol);
            }

            M.set(irow, icol, fVal);
        }

        return M;
    }

    public void multiply(float fVal)
    {
        for (int i = 0; i < mRows*mCols; i++)
        {
            mDati[i] = mDati[i] * fVal;
        }
    }

    public void sum(float fVal)
    {
        for (int i = 0; i < mRows*mCols; i++)
        {
            mDati[i] = mDati[i] + fVal;
        }
    }
}
