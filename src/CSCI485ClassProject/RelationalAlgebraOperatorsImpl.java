package CSCI485ClassProject;

import CSCI485ClassProject.models.AssignmentExpression;
import CSCI485ClassProject.models.ComparisonPredicate;
import CSCI485ClassProject.models.Iterator;
import CSCI485ClassProject.models.Record;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// your codes
public class RelationalAlgebraOperatorsImpl implements RelationalAlgebraOperators {

  @Override
  public Iterator select(String tableName, ComparisonPredicate predicate, Iterator.Mode mode, boolean isUsingIndex) {
    if (predicate.validate() == StatusCode.PREDICATE_OR_EXPRESSION_INVALID) {
      return null;
    }

    return new SelectIterator(tableName, predicate, mode, isUsingIndex);
  }

  @Override
  public Set<Record> simpleSelect(String tableName, ComparisonPredicate predicate, boolean isUsingIndex) {
    Set<Record> retval = new HashSet<Record>();

    Iterator it = new SelectIterator(tableName, predicate, Iterator.Mode.READ, isUsingIndex);
    Record curr = it.next();

    while (curr != null) {
      retval.add(curr);
      curr = it.next();
    }

    return retval;
  }

  @Override
  public Iterator project(String tableName, String attrName, boolean isDuplicateFree) {
    return new ProjectIterator(tableName, attrName, isDuplicateFree);
  }

  @Override
  public Iterator project(Iterator iterator, String attrName, boolean isDuplicateFree) {
    return new ProjectIterator(iterator, attrName, isDuplicateFree);
  }

  @Override
  public List<Record> simpleProject(String tableName, String attrName, boolean isDuplicateFree) {
    List<Record> retval = new ArrayList<Record>();

    Iterator it = new ProjectIterator(tableName, attrName, isDuplicateFree);
    Record curr = it.next();

    while (curr != null) {
      retval.add(curr);
      curr = it.next();
    }

    return retval;
  }

  @Override
  public List<Record> simpleProject(Iterator iterator, String attrName, boolean isDuplicateFree) {
    List<Record> retval = new ArrayList<Record>();

    Iterator it = new ProjectIterator(iterator, attrName, isDuplicateFree);
    Record curr = it.next();

    while (curr != null) {
      retval.add(curr);
      curr = it.next();
    }

    return retval;
  }

  @Override
  public Iterator join(Iterator outerIterator, Iterator innerIterator, ComparisonPredicate predicate, Set<String> attrNames) {
    return null;
  }

  @Override
  public StatusCode insert(String tableName, Record record, String[] primaryKeys) {
    return null;
  }

  @Override
  public StatusCode update(String tableName, AssignmentExpression assignExp, Iterator dataSourceIterator) {
    return null;
  }

  @Override
  public StatusCode delete(String tableName, Iterator iterator) {
    return null;
  }
}
