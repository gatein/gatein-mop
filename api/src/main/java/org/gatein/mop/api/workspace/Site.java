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
package org.gatein.mop.api.workspace;

/**
 * A site is a collection of pages and navigations.
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public interface Site extends WorkspaceObject, WorkspaceCustomizationContext {

  /**
   * Returns the site id.
   *
   * @return the site id
   */
  String getObjectId();

  /**
   * Returns the site name.
   *
   * @return the site name
   */
  String getName();

  /**
   * Returns a type that extends the site.
   *
   * @return the objec type
   */
  ObjectType<? extends Site> getObjectType();

  /**
   * Returns the site root navigation.
   *
   * @return the site navigation
   */
  Navigation getRootNavigation();

  /**
   * Returns the site root page.
   *
   * @return the site root page
   */
  Page getRootPage();

  /**
   * Returns the site workspace.
   *
   * @return the site workspace
   */
  Workspace getWorkspace();

  /**
   * Destroy the site.
   */
  void destroy();

}
