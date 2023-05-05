package CSCI485ClassProject;

import CSCI485ClassProject.models.AttributeType;
import CSCI485ClassProject.models.ComparisonOperator;
import CSCI485ClassProject.models.ComparisonPredicate;
import CSCI485ClassProject.models.Iterator;
import CSCI485ClassProject.models.Record;

public class SelectIterator extends Iterator {

    private Cursor cursor;
    private Records records;
    private ComparisonPredicate predicate;

    public SelectIterator(String tableName, ComparisonPredicate predicate, Iterator.Mode mode, boolean isUsingIndex) {
        this.predicate = predicate;
        this.records = new RecordsImpl();
        if (isUsingIndex) {
            this.cursor = records.openCursor(tableName, mode);
        } else {
            if (predicate.getLeftHandSideAttrType() == AttributeType.INT) {
                this.cursor = records.openCursor(tableName, predicate.getLeftHandSideAttrName(), Long.MIN_VALUE, ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, mode, isUsingIndex);
            } else if (predicate.getLeftHandSideAttrType() == AttributeType.DOUBLE) {
                this.cursor = records.openCursor(tableName, predicate.getLeftHandSideAttrName(), Double.MIN_VALUE, ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, mode, isUsingIndex);
            } else {
                this.cursor = records.openCursor(tableName, predicate.getLeftHandSideAttrName(), "", ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, mode, isUsingIndex);
            }
        }
    }

    @Override
    public Record next() {
        Record currRecord;

        if (!cursor.isInitialized()) {
            currRecord = cursor.getFirst();
        } else if (cursor.hasNext()) {
            currRecord = cursor.next(false);
        } else {
            return null;
        }

        while (!predicate.isSatisfiedBy(currRecord)) {
            if (!cursor.hasNext()) {
                return null;
            } else {
                currRecord = cursor.next(false);
            }
        }

        return currRecord;
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
