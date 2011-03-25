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
package org.gatein.mop.core.api;

import org.gatein.mop.core.api.content.CustomizationContextResolver;
import org.gatein.mop.core.api.workspace.WorkspaceImpl;
import org.gatein.mop.core.api.workspace.SiteImpl;
import org.gatein.mop.core.api.workspace.PortalSite;
import org.gatein.mop.core.api.workspace.GroupSite;
import org.gatein.mop.core.api.workspace.UserSite;
import org.gatein.mop.core.api.workspace.UIContainerImpl;
import org.gatein.mop.core.api.workspace.UIWindowImpl;
import org.gatein.mop.core.api.workspace.UIBodyImpl;
import org.gatein.mop.core.api.workspace.PageImpl;
import org.gatein.mop.core.api.workspace.NavigationImpl;
import org.gatein.mop.core.api.workspace.WorkspaceObjectImpl;
import org.gatein.mop.core.api.workspace.UIComponentImpl;
import org.gatein.mop.core.api.workspace.PageLinkImpl;
import org.gatein.mop.core.api.workspace.URLLinkImpl;
import org.gatein.mop.api.workspace.WorkspaceCustomizationContext;
import org.gatein.mop.core.api.workspace.content.ContextSpecialization;
import org.gatein.mop.core.api.workspace.content.AbstractCustomization;
import org.gatein.mop.api.content.CustomizationContext;
import org.gatein.mop.api.content.Customization;
import org.gatein.mop.api.workspace.Workspace;
import org.gatein.mop.api.workspace.ObjectType;
import org.gatein.mop.api.workspace.WorkspaceObject;
import org.gatein.mop.api.Model;
import org.chromattic.api.ChromatticSession;
import org.chromattic.api.event.LifeCycleListener;

import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class ModelImpl implements Model
{

   /** . */
   private static final Map<ObjectType<?>, Class<? extends WorkspaceObjectImpl>> typeToClassImpl;

   static
   {
      Map<ObjectType<?>, Class<? extends WorkspaceObjectImpl>> tmp = new HashMap<ObjectType<?>, Class<? extends WorkspaceObjectImpl>>();
      tmp.put(ObjectType.ANY, WorkspaceObjectImpl.class);
      tmp.put(ObjectType.WORKSPACE, WorkspaceImpl.class);
      tmp.put(ObjectType.SITE, SiteImpl.class);
      tmp.put(ObjectType.PORTAL_SITE, PortalSite.class);
      tmp.put(ObjectType.GROUP_SITE, GroupSite.class);
      tmp.put(ObjectType.USER_SITE, UserSite.class);
      tmp.put(ObjectType.PAGE, PageImpl.class);
      tmp.put(ObjectType.NAVIGATION, NavigationImpl.class);
      tmp.put(ObjectType.COMPONENT, UIComponentImpl.class);
      tmp.put(ObjectType.BODY, UIBodyImpl.class);
      tmp.put(ObjectType.CONTAINER, UIContainerImpl.class);
      tmp.put(ObjectType.WINDOW, UIWindowImpl.class);
      tmp.put(ObjectType.PAGE_LINK, PageLinkImpl.class);
      tmp.put(ObjectType.URL_LINK, URLLinkImpl.class);
      typeToClassImpl = tmp;
   }

   /** . */
   private final ChromatticSession session;

   /** . */
   private WorkspaceImpl workspace;

   /** . */
   private final MOPService mop;

   /** . */
   private final CustomizationContextResolver customizationContextResolver = new CustomizationContextResolver()
   {
      public CustomizationContext resolve(String contextType, String contextId)
      {
         if (WorkspaceCustomizationContext.TYPE.equals(contextType))
         {
            return findObjectById(ObjectType.WINDOW, contextId);
         }
         else
         {
            return mop.customizationContextResolvers.resolve(contextType, contextId);
         }
      }
   };

   public ModelImpl(MOPService mop, ChromatticSession session)
   {
      this.session = session;
      this.mop = mop;

      //
      session.addEventListener(contextualizer);
   }

   public ChromatticSession getSession()
   {
      return session;
   }

   public Workspace getWorkspace()
   {
      return getWorkspaceImpl();
   }

   public <A> A getAdapter(Object o, Class<A> adaptedType, boolean adapt)
   {
      Class<? extends A> adapterType = mop.getConcreteAdapterType(adaptedType);
      if (adapterType == null) {
         adapterType = adaptedType;
      }
      return _getAdapter(o, adapterType, adapt);
   }

   private <A> A _getAdapter(Object o, Class<A> type, boolean adapt) {
      A a = session.getEmbedded(o, type);
      if (a == null && adapt) {
         a = session.create(type);
         session.setEmbedded(o, type, a);
      }
      return a;
   }

   private WorkspaceImpl getWorkspaceImpl()
   {
      if (workspace == null)
      {
         workspace = session.findByPath(WorkspaceImpl.class, "mop:workspace");
         if (workspace == null)
         {
            workspace = session.insert(WorkspaceImpl.class, "mop", "workspace");
         }
      }
      return workspace;
   }

   public void save()
   {
      session.save();
   }

   public void close()
   {
      session.close();
   }

   private final LifeCycleListener contextualizer = new LifeCycleListener()
   {
      public void created(Object o)
      {
         inject(o, false);
      }

      public void loaded(String id, String path, String name, Object o)
      {
         inject(o, true);
      }

      public void added(String id, String path, String name, Object o)
      {
         inject(o, true);
      }

      public void removed(String id, String path, String name, Object o)
      {
      }
   };

   public Customization<?> findCustomizationById(String id)
   {
      return session.findById(Customization.class, id);
   }

   public <O extends WorkspaceObject> Iterator<O> findObject(ObjectType<O> type, String statement)
   {
      Class<O> impl = (Class<O>)typeToClassImpl.get(type);
      return session.createQueryBuilder(impl).where(statement).get().objects();
   }

   public String pathOf(WorkspaceObject o)
   {
      return session.getPath(o);
   }

   public <O extends WorkspaceObject> O findObjectByPath(ObjectType<? extends O> type, String path)
   {
      Class<? extends O> t = type.getJavaType();
      return session.findByPath(t, path);
   }

   private void inject(Object o, boolean persistent)
   {
      if (o instanceof AbstractCustomization)
      {
         ((AbstractCustomization)o).session = session;
         ((AbstractCustomization)o).registry = mop.contentManagerRegistry;
      }
      else if (o instanceof ContextSpecialization)
      {
         ((ContextSpecialization)o).setCustomizationContextResolver(customizationContextResolver);
      }
      else if (o instanceof WorkspaceObjectImpl)
      {
         ((WorkspaceObjectImpl)o).model = this;
      }
   }

   public <O extends WorkspaceObject> O findObjectById(ObjectType<O> type, String id)
   {
      Class<? extends WorkspaceObjectImpl> impl = typeToClassImpl.get(type);
      WorkspaceObjectImpl object = session.findById(impl, id);
      if (object != null)
      {
         return type.cast(object);
      }
      else
      {
         return null;
      }
   }
}
