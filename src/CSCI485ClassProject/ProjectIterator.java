package CSCI485ClassProject;

import java.util.List;

import com.apple.foundationdb.Database;
import com.apple.foundationdb.Transaction;

import CSCI485ClassProject.fdb.FDBHelper;
import CSCI485ClassProject.fdb.FDBKVPair;
import CSCI485ClassProject.models.AttributeType;
import CSCI485ClassProject.models.ComparisonOperator;
import CSCI485ClassProject.models.IndexType;
import CSCI485ClassProject.models.Iterator;
import CSCI485ClassProject.models.Record;
import CSCI485ClassProject.models.TableMetadata;

public class ProjectIterator extends Iterator {

    private Iterator cursor;
    private Records records;
    private String  attrName;
    private boolean noDuplicates;

    public ProjectIterator(String tableName, String attrName, boolean isDuplicateFree) {
        this.records = new RecordsImpl();
        if (isDuplicateFree) {
            AttributeType attrType = (new TableManagerImpl()).listTables().get(tableName).getAttributes().get(attrName);
            Indexes indexes = new IndexesImpl(records);
            indexes.createIndex(tableName, attrName, IndexType.NON_CLUSTERED_B_PLUS_TREE_INDEX);
            if (attrType == AttributeType.INT) {
                this.cursor = records.openCursor(tableName, attrName, Long.MIN_VALUE, ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, Mode.READ, true);
            }
            else if (attrType == AttributeType.DOUBLE) {
                this.cursor = records.openCursor(tableName, attrName, Double.MIN_VALUE, ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, Mode.READ, true);
            }
            else if (attrType == AttributeType.VARCHAR) {
                this.cursor = records.openCursor(tableName, attrName, "", ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, Mode.READ, true);
            }
            else {
                this.cursor = records.openCursor(tableName, Mode.READ);
            }
        }
        else {
            this.cursor = records.openCursor(tableName, Cursor.Mode.READ);
        }
        this.noDuplicates = isDuplicateFree;
        this.attrName = attrName;
    }

    public ProjectIterator(Iterator iterator, String attrName, boolean isDuplicateFree) {
        this.records = new RecordsImpl();
        this.cursor = iterator;
        this.attrName = attrName;
        this.noDuplicates = isDuplicateFree;
    }

    @Override
    public Record next() {
        Record currRecord;

        if (cursor instanceof Cursor) {
            Cursor temp = (Cursor) cursor;
            if (!temp.isInitialized()) {
                currRecord = temp.getFirst();
            } else if (noDuplicates) {
                Record prevRecord = temp.getCurrentRecord();
                currRecord = temp.next();

                while (currRecord != null && prevRecord.getValueForGivenAttrName(attrName).equals(currRecord.getValueForGivenAttrName(attrName))) {
                    if (!temp.hasNext()) {
                        currRecord = null;
                    } else {
                        currRecord = temp.next();
                    }
                }
            } else if (temp.hasNext()) {
                currRecord = temp.next();
            } else {
                currRecord = null;
            }
        } else {
            currRecord = cursor.next();
        }

        if (currRecord == null) {
            return null;
        } else {
            Record projectedRecord = new Record();
            projectedRecord.setAttrNameAndValue(attrName, currRecord.getValueForGivenAttrName(attrName));
            return projectedRecord;
        }
    }

    @Override
    public void commit() {
        cursor.commit();
    }

    @Override
    public void abort() {
        cursor.abort();
    }
    
}
