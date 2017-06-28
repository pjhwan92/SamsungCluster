package alluxio;

import com.google.common.primitives.Booleans;
import com.google.common.primitives.Ints;

import java.util.*;
import alluxio.master.file.meta.InodeTree;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pjh on 6/15/17.
 */
public class AlluxioSplit {
    private static final Logger LOG = LoggerFactory.getLogger(Constants.LOGGER_TYPE);

    private List<AlluxioURI> mPaths = null;
    private List<Integer> mStarts = null;
    private List<Integer> mLengths = null;
    private List<Boolean> mPrepared = null;
    private Map<AlluxioURI, List<Integer>> mFileBlockMap = null;
    private Map<AlluxioURI, Long> mFileIdMap = null;

    public void initializeMapInfo (InodeTree tree) {
        if (mFileBlockMap.size() > 0) {
        }
        for (AlluxioURI path : mPaths) {
        }
    }

    public AlluxioSplit () {
        mPaths = new ArrayList<> ();
        mStarts = new ArrayList<> ();
        mLengths = new ArrayList<> ();
        mPrepared = new ArrayList<> ();
        mFileBlockMap = new HashMap<>();
        mFileIdMap = new HashMap<> ();
    }

    public AlluxioSplit (AlluxioURI[] paths, int[] starts, int[] lengths) {
        mPaths = new ArrayList<> (Arrays.asList(paths));
        mStarts  = new ArrayList<> (Ints.asList (starts));
        mLengths = new ArrayList<> (Ints.asList (lengths));
        mPrepared = new ArrayList<Boolean> (Booleans.asList (new boolean[mPaths.size()]));
        mFileBlockMap = new HashMap <> ();
        mFileIdMap = new HashMap <> ();
    }

    public List<AlluxioURI> getPaths() {
        return mPaths;
    }

    public List<Integer> getStarts() {
        return mStarts;
    }

    public List<Integer> getLengths() {
        return mLengths;
    }

}
