/**
 * Copyright (C) 2009 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.gatein.mop.api;

import org.gatein.mop.api.workspace.Workspace;
import org.gatein.mop.api.workspace.WorkspaceObject;
import org.gatein.mop.api.workspace.ObjectType;
import org.gatein.mop.api.content.Customization;

import java.util.Iterator;

/**
 * Provides access to the model.  
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public interface Model {

  /**
   * Returns the workspace.
   *
   * @return the workspace
   */
  Workspace getWorkspace();

  /**
   * Returns a specified object or null if it cannot be found.
   *
   * @param type the object type
   * @param id the object id
   * @param <O> the object type parameter
   * @return the object
   */
  <O extends WorkspaceObject> O findObjectById(ObjectType<O> type, String id);

  <O extends WorkspaceObject> O findObjectByPath(ObjectType<? extends O> type, String path);

  <O extends WorkspaceObject> Iterator<O> findObject(ObjectType<O> type, String statement);

  String pathOf(WorkspaceObject o);

  Customization<?> findCustomizationById(String id);

  void save();

  void close();

}
