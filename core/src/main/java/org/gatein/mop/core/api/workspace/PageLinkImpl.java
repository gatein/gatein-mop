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

import org.chromattic.api.annotations.NodeMapping;
import org.chromattic.api.annotations.ManyToOne;
import org.chromattic.api.annotations.MappedBy;
import org.chromattic.api.annotations.Property;
import org.chromattic.api.annotations.Path;
import org.chromattic.api.RelationshipType;
import org.gatein.mop.api.workspace.link.PageLink;
import org.gatein.mop.api.workspace.Page;
import org.gatein.mop.api.workspace.ObjectType;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
@NodeMapping(name = "mop:pagelink")
public abstract class PageLinkImpl extends LinkImpl implements PageLink
{

   @ManyToOne(type = RelationshipType.PATH)
   @MappedBy("page")
   public abstract PageImpl getPage();

   public abstract void setPage(PageImpl page);

   @Path
   @Property(name = "path")
   public abstract String getPathPath();

   public abstract void setPagePath(String path);

   public void setPage(Page page)
   {
      setPage((PageImpl)page);
   }

   public ObjectType<? extends PageLink> getObjectType()
   {
      return ObjectType.PAGE_LINK;
   }
}
