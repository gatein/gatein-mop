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
package org.gatein.mop.core.api.workspace;

import org.chromattic.api.annotations.FormattedBy;
import org.chromattic.api.annotations.NamingPrefix;
import org.chromattic.api.annotations.OneToMany;
import org.chromattic.api.annotations.Create;
import org.chromattic.api.annotations.PrimaryType;
import org.chromattic.ext.format.BaseEncodingObjectFormatter;

import java.util.Collection;
import java.util.Map;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
@NamingPrefix("mop")
@FormattedBy(BaseEncodingObjectFormatter.class)
@PrimaryType(name = "foo")
public abstract class SiteContainer<T extends SiteImpl>
{

   @OneToMany
   public abstract Map<String, T> getSites();

   public abstract WorkspaceImpl getWorkspace();

   public T addSite(String siteName)
   {
      T site = createSite();
      getSites().put(siteName, site);
      return site;
   }

   @Create
   public abstract T createSite();

   public T getSite(String name)
   {
      Map<String, T> sites = getSites();
      return sites.get(name);
   }

   public Collection<T> getAllSites()
   {
      Map<String, T> sites = getSites();
      return sites.values();
   }
}
