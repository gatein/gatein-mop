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

import org.chromattic.api.ChromatticSession;
import org.chromattic.api.annotations.*;
import org.chromattic.ext.format.BaseEncodingObjectFormatter;
import org.gatein.mop.api.workspace.WorkspaceObject;
import org.gatein.mop.api.workspace.ObjectType;
import org.gatein.mop.api.content.CustomizationContext;
import org.gatein.mop.core.api.AttributesImpl;
import org.gatein.mop.core.api.ModelImpl;
import org.gatein.mop.spi.AdapterLifeCycle;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
@NamingPrefix("mop")
@FormattedBy(BaseEncodingObjectFormatter.class)
@PrimaryType(name = "mop:workspaceobject")
public abstract class WorkspaceObjectImpl implements WorkspaceObject
{

   /** . */
   public ModelImpl model;

   /** . */
   private Map<Class<?>, Object> adapters;

   @Name
   public abstract String getNodeName();

   public abstract void setNodeName(String name);

   @Id
   public abstract String getObjectId();

   @OneToOne
   @MappedBy("mop:attributes")
   @Owner
   public abstract AttributesImpl getAttributes();

   public final <A> A adapt(Class<A> adapterType)
   {
      // Lookup in map first
      if (adapters != null)
      {
         Object adapter = adapters.get(adapterType);
         if (adapter != null)
         {
            return (A)adapter;
         }
      }

      //
      A adapter;
      if (model.isAdaptable(adapterType))
      {
         AdapterLifeCycle<Object, A> lifeCycle = model.getAdapter(this, adapterType);
         if (lifeCycle != null)
         {
            if (adapters == null)
            {
               adapters = new HashMap<Class<?>, Object>();
            }
            adapter = lifeCycle.create(this, adapterType);
            adapters.put(adapterType, adapter);
         }
         else
         {
            adapter = null;
         }
      }
      else
      {
         adapter = getMixin(adapterType, true);
      }

      //
      return adapter;
   }

   public final <A> boolean isAdapted(Class<A> adapterType)
   {
      if (adapters != null)
      {
         if (adapters.containsKey(adapterType))
         {
            return true;
         }
      }

      //
      if (model.isAdaptable(adapterType))
      {
         return false;
      }
      else
      {
         return getMixin(adapterType, false) != null;
      }
   }

   public <A> void removeAdapter(Class<A> adapterType)
   {
      if (model.isAdaptable(adapterType))
      {
         if (adapters != null)
         {
            A adapter = (A)adapters.remove(adapterType);
            if (adapter != null)
            {
               AdapterLifeCycle<Object, A> lifeCycle = model.getAdapter(this, adapterType);
               lifeCycle.destroy(adapter, this, adapterType);
            }
         }
      }
      else
      {
         removeMixin(adapterType);
      }
   }

   private <A> A getMixin(Class<A> type, boolean create)
   {
      ChromatticSession session = model.getSession();
      A a = session.getEmbedded(this, type);
      if (a == null && create)
      {
         a = session.create(type);
         session.setEmbedded(this, type, a);
      }
      return a;
   }

   private <A> void removeMixin(Class<A> type)
   {
      ChromatticSession session = model.getSession();
      session.setEmbedded(this, type, null);
   }

   public String getName()
   {
      return getNodeName();
   }

   @Override
   public String toString()
   {
      ObjectType<?> objectType = getObjectType();
      Class<? extends WorkspaceObject> javaType = objectType.getJavaType();
      String typeName = javaType.getSimpleName();
      String name = getNodeName();
      String id = getObjectId();
      return typeName + "[name=" + name + ",id=" + id + "]";
   }

   static boolean contains(CustomizationContext container, CustomizationContext contained)
   {
      return contains(((WorkspaceCustomizationContextImpl)container).getOwner(), contained);
   }

   private static boolean contains(WorkspaceObjectImpl container, CustomizationContext contained)
   {
      if (container == null)
      {
         throw new NullPointerException("No null container accepted");
      }
      if (contained == null)
      {
         throw new NullPointerException("No null contained accepted");
      }



      //
      if (container == contained)
      {
         return true;
      }
      if (container instanceof WorkspaceImpl)
      {
         if (contained instanceof SiteImpl)
         {
            SiteImpl site = (SiteImpl)contained;
            return contains(container, site.getWorkspace().getCustomizationContext());
         }
         else if (contained instanceof PageImpl)
         {
            PageImpl page = (PageImpl)contained;
            return contains(container, page.getSite().getCustomizationContext());
         }
         else if (contained instanceof UIWindowImpl)
         {
            UIWindowImpl window = (UIWindowImpl)contained;
            return contains(container, window.getPage().getCustomizationContext());
         }
      }
      if (container instanceof SiteImpl)
      {
         if (contained instanceof PageImpl)
         {
            PageImpl page = (PageImpl)contained;
            return contains(container, page.getSite().getCustomizationContext());
         }
         else if (contained instanceof UIWindowImpl)
         {
            UIWindowImpl window = (UIWindowImpl)contained;
            return contains(container, window.getPage().getCustomizationContext());
         }
      }
      if (container instanceof PageImpl)
      {
         if (contained instanceof UIWindowImpl)
         {
            UIWindowImpl window = (UIWindowImpl)contained;
            return contains(container, window.getPage().getCustomizationContext());
         }
      }
      return false;
   }
}
