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

import java.util.Collection;

/**
 * The workspace.
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public interface Workspace extends WorkspaceObject, WorkspaceCustomizationContext {

  /**
   * Returns the default share site.
   *
   * @return the default shared site
   */
  Site getSharedSite();

  /**
   * Returns a specified site or null if it cannot be found.
   *
   * @param siteType the site type
   * @param siteName the site name
   * @return the site
   */
  <S extends Site> S getSite(ObjectType<S> siteType, String siteName);

  /**
   * Returns the sites of a given type.
   *
   * @param siteType the site type
   * @return the sites
   */
  <S extends Site> Collection<S> getSites(ObjectType<S> siteType);

  /**
   * Returns all the sites.
   *
   * @return the sites
   */
  Collection<Site> getSites();

  /**
   * Creates a new site.
   *
   * @param siteType the site type
   * @param name the site name
   * @return the new site
   */
  <S extends Site> S addSite(ObjectType<S> siteType, String name);

  /**
   * Returns a specified object or null if it cannot be found.
   *
   * @param type the object type
   * @param id the object id
   * @param <O> the object type parameter
   * @return the object
   */
  <O extends WorkspaceObject> O getObject(ObjectType<O> type, String id);
}
