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
import org.chromattic.api.annotations.Destroy;
import org.chromattic.api.annotations.Owner;
import org.chromattic.api.annotations.PrimaryType;
import org.chromattic.api.annotations.MappedBy;
import org.chromattic.api.RelationshipType;
import org.gatein.mop.api.workspace.Navigation;
import org.gatein.mop.api.workspace.link.Link;
import org.gatein.mop.api.workspace.ObjectType;
import org.gatein.mop.api.workspace.Site;
import org.gatein.mop.api.workspace.link.PageLink;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
@PrimaryType(name = "mop:navigation")
public abstract class NavigationImpl extends WorkspaceObjectImpl implements Navigation
{

   @OneToOne
   @MappedBy("mop:rootnavigation")
   public abstract SiteImpl getParentSite();

   @OneToOne
   @MappedBy("mop:children")
   @Owner
   public abstract NavigationContainer getChildrenContainer();

   @ManyToOne
   public abstract NavigationContainer getParentContainer();

   @OneToOne
   @MappedBy("mop:link")
   @Owner
   public abstract LinkImpl getLink();

   public abstract LinkImpl setLink(LinkImpl target);

   @Create
   public abstract NavigationImpl createNavigation(String name);

   @Create
   public abstract URLLinkImpl createURLTarget();

   @Create
   public abstract PageLinkImpl createPageLink();

   @Destroy
   public abstract void destroy();

   @OneToOne(type = RelationshipType.EMBEDDED)
   @Owner
   public abstract TemplatizedImpl getTemplatized();

   public abstract void setTemplatized(TemplatizedImpl templatized);

   public String getName()
   {
      return getNodeName();
   }

   public void setName(String name)
   {
      // Capture the current index
      int index = getIndex();

      // Rename (will change the index to the last / JCR move)
      setNodeName(name);

      // Set index back to original value
      List<NavigationImpl> siblings = getParentContainer().getNavigationList();
      siblings.add(index, this);
   }

   public int getIndex()
   {
      NavigationImpl parent = getParent();
      if (parent == null)
      {
         return 0;
      }
      else
      {
         return parent.getChildren().indexOf(this);
      }
   }

   public ObjectType<? extends Navigation> getObjectType()
   {
      return ObjectType.NAVIGATION;
   }

   public NavigationImpl getParent()
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

   public List<Navigation> getChildren()
   {
      NavigationContainer childrenContainer = getChildrenContainer();

      // Yeah a bit ugly but navigation list should support type safety itself
      // (i.e only accept object of type NavigationImpl
      return (List)childrenContainer.getNavigationList();
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

   public NavigationImpl addChild(Integer index, String name) throws NullPointerException, IndexOutOfBoundsException, IllegalArgumentException
   {
      NavigationContainer childrenContainer = getChildrenContainer();
      return childrenContainer.addNavigation(index, name);
   }

   public NavigationImpl addChild(String name)
   {
      return addChild(null, name);
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
