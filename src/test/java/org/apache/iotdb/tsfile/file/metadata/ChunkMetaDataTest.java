package org.apache.iotdb.tsfile.file.metadata;

import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
import org.apache.iotdb.tsfile.file.metadata.utils.TestHelper;
import org.apache.iotdb.tsfile.file.metadata.utils.Utils;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

public class ChunkMetaDataTest {

  public static final String MEASUREMENT_UID = "sensor231";
  public static final long FILE_OFFSET = 2313424242L;
  public static final long NUM_OF_POINTS = 123456L;
  public static final long START_TIME = 523372036854775806L;
  public static final long END_TIME = 523372036854775806L;
  public static final TSDataType DATA_TYPE = TSDataType.INT64;
  final String PATH = "target/outputTimeSeriesChunk.tsfile";

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
    File file = new File(PATH);
    if (file.exists())
      file.delete();
  }

  @Test
  public void testWriteIntoFile() throws IOException {
    ChunkMetaData metaData = TestHelper.createSimpleTimeSeriesChunkMetaData();
    serialized(metaData);
    ChunkMetaData readMetaData = deSerialized();
    Utils.isTimeSeriesChunkMetadataEqual(metaData, readMetaData);
    serialized(readMetaData);
  }

  private ChunkMetaData deSerialized() {
    FileInputStream fis = null;
    ChunkMetaData metaData = null;
    try {
      fis = new FileInputStream(new File(PATH));
      metaData = ChunkMetaData.deserializeFrom(fis);
      return metaData;
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (fis != null) {
        try {
          fis.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return metaData;
  }


  private void serialized(ChunkMetaData metaData){
    File file = new File(PATH);
    if (file.exists()){
      file.delete();
    }
    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(file);
      metaData.serializeTo(fos);
    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      if(fos!=null){
        try {
          fos.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}