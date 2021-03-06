/*******************************************************************************
*
* Pentaho Big Data
*
* Copyright (C) 2002-2018 by Hitachi Vantara : http://www.pentaho.com
*
*******************************************************************************
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with
* the License. You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
******************************************************************************/

package org.pentaho.cassandra.spi;

import java.util.List;

import org.pentaho.cassandra.util.Selector;
import org.pentaho.di.core.row.ValueMetaInterface;

/**
 * Interface to something that can fetch and represent meta data
 * on a Cassandra table
 * 
 * @author Mark Hall (mhall{[at]}pentaho{[dot]}com)
 */
public interface ITableMetaData {

  /**
   * Set the keyspace for this table
   * 
   * @param keyspace the keyspace to use
   */
  void setKeyspace( Keyspace keyspace );

  /**
   * Set the name of this table
   * 
   * @param tableName the name of this table
   */
  void setTableName( String tableName );

  /**
   * Get the name of this table
   * 
   * @return the name of this table
   */
  String getTableName();

  /**
   * Get a textual description of this table with as much information as
   * the underlying driver provides
   * 
   * @return a textual description of this table
   * @throws Exception if a problem occurs
   */
  String describe() throws Exception;

  /**
   * Returns true if the named column is explicitly defined in this column
   * family. Considers both key and columns
   * 
   * @param colName the name of the column to check for
   * @return true if the named column is explicitly defined
   */
  boolean columnExistsInSchema( String colName );

  /**
   * Return the appropriate Kettle type for the table's key. This should
   * handle a composite key value too - i.e. only Kettle type String can be used
   * as a catch-all for all composite possibilities
   * 
   * @return the appropriate Kettle type for the table's key
   */
  ValueMetaInterface getValueMetaForKey();

  /**
   * Get the names of the columns that make up the key in this table. If
   * there is a single key column and it does not have an explicit alias set
   * then this will use the string "KEY".
   * 
   * @return the name(s) of the columns that make up the key
   */
  List<String> getKeyColumnNames();

  /**
   * Return the appropriate Kettle type for the named column. If the column is
   * not explicitly named in the table schema then the Kettle type
   * equivalent to the default validation class should be returned. Note that
   * columns that make up a composite key should be covered by this method too -
   * i.e. the appropriate Kettle type for each should be returned
   * 
   * @param colName the Cassandra column name to get the Kettle type for
   * @return the Kettle type for the named column.
   */
  ValueMetaInterface getValueMetaForColumn( String colName );

  /**
   * Return a list of Kettle types for all the columns explicitly defined in
   * this table (not including the default validator).
   * 
   * @return a list of Kettle types for explicitly defined columns in this
   *         table
   */
  List<ValueMetaInterface> getValueMetasForSchema();

  /**
   * Return the appropriate Kettle type for the selector depending on id this is column or function. If the column is
   * not explicitly named in the table schema or the function is incorrect named then the Kettle type
   * equivalent to the default validation class should be returned.
   * @param selector the selector that corresponds either to Cassandra column name or Cassandra function to get the Kettle type
   * @return the Kettle type for the selector
   */
  ValueMetaInterface getValueMeta( Selector selector );
}
