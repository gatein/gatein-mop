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

import org.chromattic.api.annotations.OneToMany;
import org.chromattic.api.annotations.OneToOne;
import org.chromattic.api.annotations.ManyToOne;
import org.chromattic.api.annotations.PrimaryType;
import org.chromattic.api.annotations.RelatedMappedBy;
import org.chromattic.api.annotations.MappedBy;
import org.chromattic.api.annotations.Destroy;
import org.chromattic.api.RelationshipType;
import org.gatein.mop.api.workspace.Page;
import org.gatein.mop.api.workspace.ObjectType;
import org.gatein.mop.api.workspace.Navigation;
import org.gatein.mop.api.workspace.WorkspaceObject;
import org.gatein.mop.api.workspace.TemplatizedObject;
import org.gatein.mop.api.workspace.link.PageLink;
import org.gatein.mop.api.Attributes;
import org.gatein.mop.core.util.AbstractAttributes;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Set;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
@PrimaryType(name = "mop:page")
public abstract class PageImpl extends WorkspaceObjectImpl implements Page
{

   /** . */
   private final Attributes cascadingAttributes = new AbstractAttributes()
   {
      public Set<String> getKeys()
      {
         throw new UnsupportedOperationException("todo ?");
      }

      protected Object get(String name)
      {
         return getCascadedPropertyValue(name);
      }

      protected void set(String name, Object o)
      {
         throw new UnsupportedOperationException("read only");
      }
   };

   @ManyToOne(type = RelationshipType.PATH)
   @MappedBy("mop:template")
   public abstract PageImpl getPageTemplate();

   public abstract void setPageTemplate(PageImpl template);

   @OneToMany(type = RelationshipType.PATH)
   @RelatedMappedBy("mop:template")
   public abstract Collection<NavigationImpl> getTemplatizedNavigations();

   @OneToMany(type = RelationshipType.PATH)
   @RelatedMappedBy("mop:template")
   public abstract Collection<PageImpl> getTemplatizedPages();

   @OneToMany(type = RelationshipType.PATH)
   @RelatedMappedBy("mop:template")
   public abstract Collection<? extends WorkspaceObject> getTemplatizedObjects();

   @OneToOne
   @MappedBy("children")
   public abstract PageContainer getChildrenContainer();

   @ManyToOne
   public abstract PageContainer getParentContainer();

   @OneToOne
   @RelatedMappedBy("rootpage")
   public abstract SiteImpl getSiteParent();

   @OneToOne
   @MappedBy("rootcomponent")
   public abstract UIContainerImpl getRootComponent();

   @Destroy
   public abstract void destroy();

   @OneToOne(type = RelationshipType.EMBEDDED)
   public abstract WorkspaceCustomizationContextImpl getCustomizationContext();

   // *******************************************************************************************************************

   public <T extends TemplatizedObject> Collection<? extends T> getTemplatizedObjects(ObjectType<T> templatizedType)
   {
      if (Page.class.equals(templatizedType.getJavaType()))
      {
         ArrayList bilto = new ArrayList();
         for (Page page : getTemplatizedPages())
         {
            bilto.add(page);
         }
         return bilto;
      }
      else if (Navigation.class.isAssignableFrom(templatizedType.getJavaType()))
      {
         ArrayList bilto = new ArrayList();
         for (Navigation page : getTemplatizedNavigations())
         {
            bilto.add(page);
         }
         return bilto;
      }
      else
      {
         throw new IllegalArgumentException("Unaccepted templatized type");
      }
   }

   public ObjectType<? extends Page> getObjectType()
   {
      return ObjectType.PAGE;
   }

   public Attributes getCascadingAttributes()
   {
      return cascadingAttributes;
   }

   public SiteImpl getSite()
   {
      PageContainer parent = getParentContainer();
      if (parent != null)
      {
         return parent.getOwner().getSite();
      }
      else
      {
         return getSiteParent();
      }
   }

   public PageImpl getParent()
   {
      PageContainer parent = getParentContainer();
      if (parent != null)
      {
         return parent.getOwner();
      }
      else
      {
         return null;
      }
   }

   public PageImpl addChild(String name) throws NullPointerException, IllegalArgumentException
   {
      PageContainer childrenContainer = getChildrenContainer();
      return childrenContainer.addPage(name);
   }

   public Collection<Page> getChildren()
   {
      PageContainer childrenContainer = getChildrenContainer();
      return (Collection)childrenContainer.getPages().values();
   }

   public PageImpl getChild(String name)
   {
      if (name == null)
      {
         throw new NullPointerException();
      }
      PageContainer childrenContainer = getChildrenContainer();
      return childrenContainer.getPages().get(name);
   }

   public Collection<PageLink> getNavigations()
   {
      throw new UnsupportedOperationException();
   }

   public void setTemplate(Page template)
   {
      setPageTemplate((PageImpl)template);
   }

   public Page getTemplate()
   {
      return getPageTemplate();
   }

   private Object getCascadedPropertyValue(String propertyName)
   {
      Attributes attributes = getAttributes();
      Object value = attributes.getObject(propertyName);
      if (value == null)
      {
         PageImpl parent = getParent();
         if (parent != null)
         {
            value = parent.getCascadedPropertyValue(propertyName);
         }
      }
      return value;
   }
}
