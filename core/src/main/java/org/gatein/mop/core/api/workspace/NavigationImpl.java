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

import org.chromattic.api.annotations.OneToOne;
import org.chromattic.api.annotations.Create;
import org.chromattic.api.annotations.MappedBy;
import org.chromattic.api.annotations.ManyToOne;
import org.chromattic.api.annotations.NodeMapping;
import org.chromattic.api.annotations.Destroy;
import org.chromattic.api.annotations.RelatedMappedBy;
import org.chromattic.api.RelationshipType;
import org.gatein.mop.api.workspace.Navigation;
import org.gatein.mop.api.workspace.Page;
import org.gatein.mop.api.workspace.link.Link;
import org.gatein.mop.api.workspace.ObjectType;
import org.gatein.mop.api.workspace.Site;
import org.gatein.mop.api.workspace.link.PageLink;

import java.util.List;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
@NodeMapping(name = "mop:navigation")
public abstract class NavigationImpl extends WorkspaceObjectImpl implements Navigation
{

   @OneToOne
   @RelatedMappedBy("rootnavigation")
   public abstract SiteImpl getParentSite();

   @OneToOne
   @MappedBy("children")
   public abstract NavigationContainer getChildrenContainer();

   @ManyToOne
   public abstract NavigationContainer getParentContainer();

   @OneToOne
   @MappedBy("link")
   public abstract LinkImpl getLink();

   public abstract LinkImpl setLink(LinkImpl target);

   @Create
   public abstract NavigationImpl createNavigation(String name);

   @Create
   public abstract URLLinkImpl createURLTarget();

   @Create
   public abstract PageLinkImpl createPageLink();

   @ManyToOne(type = RelationshipType.PATH)
   @MappedBy("template")
   public abstract PageImpl getPageTemplate();

   public abstract void setPageTemplate(PageImpl template);

   @Destroy
   public abstract void destroy();

   public ObjectType<? extends Navigation> getObjectType()
   {
      return ObjectType.NAVIGATION;
   }

   public Page getTemplate()
   {
      return getPageTemplate();
   }

   public void setTemplate(Page template)
   {
      setPageTemplate((PageImpl)template);
   }

   public Navigation getParent()
   {
      NavigationContainer parent = getParentContainer();
      if (parent != null)
      {
         return parent.getOwner();
      }
      else
      {
         return null;
      }
   }

   public List<? extends Navigation> getChildren()
   {
      NavigationContainer childrenContainer = getChildrenContainer();
      return childrenContainer.getNavigationList();
   }

   public Navigation getChild(String name)
   {
      if (name == null)
      {
         throw new NullPointerException();
      }
      NavigationContainer childrenContainer = getChildrenContainer();
      return childrenContainer.getNavigationMap().get(name);
   }

   public NavigationImpl addChild(String name)
   {
      NavigationContainer childrenContainer = getChildrenContainer();
      return childrenContainer.addNavigation(name);
   }

   public <L extends Link> L linkTo(ObjectType<L> linkType)
   {
      setLink(null);

      //
      L link = null;
      if (linkType != null)
      {
         if (linkType.getJavaType().equals(PageLink.class))
         {
            PageLinkImpl pl = createPageLink();
            setLink(pl);
            link = (L)pl;
         }
         else
         {
            throw new UnsupportedOperationException("Links of type " + linkType + " are not supported");
         }
      }

      //
      return link;
   }

   public Site getSite()
   {
      SiteImpl parent = getParentSite();
      if (parent != null)
      {
         return parent;
      }
      else
      {
         return getParent().getSite();
      }
   }
}
