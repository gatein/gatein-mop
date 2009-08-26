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

import org.gatein.mop.core.api.content.ContentManagerImpl;
import org.gatein.mop.core.api.content.ContentManagerRegistry;
import org.gatein.mop.core.api.content.CustomizationContextProviderRegistry;
import org.gatein.mop.core.api.content.CustomizationContextResolver;
import org.gatein.mop.core.api.workspace.WorkspaceImpl;
import org.gatein.mop.api.workspace.WorkspaceCustomizationContext;
import org.gatein.mop.core.api.workspace.UIWindowImpl;
import org.gatein.mop.core.api.workspace.content.ContextSpecialization;
import org.gatein.mop.core.api.workspace.content.WorkspaceClone;
import org.gatein.mop.api.content.ContentManager;
import org.gatein.mop.api.content.CustomizationContext;
import org.gatein.mop.api.workspace.Workspace;
import org.gatein.mop.api.workspace.ObjectType;
import org.gatein.mop.api.workspace.WorkspaceObject;
import org.gatein.mop.api.Model;
import org.chromattic.api.LifeCycleListener;
import org.chromattic.api.ChromatticSession;
import org.chromattic.api.query.QueryLanguage;
import org.chromattic.api.query.Query;

import java.util.Iterator;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class ModelImpl implements Model {

  /** . */
  private final ChromatticSession session;

  /** . */
  private final ContentManagerRegistry contentManagers;

  /** . */
  private final CustomizationContextProviderRegistry customizationContextResolvers;

  /** . */
  private final ContentManagerImpl contentManager;

  /** . */
  private WorkspaceImpl workspace;

  /** . */
  private final CustomizationContextResolver customizationContextResolver = new CustomizationContextResolver() {
    public CustomizationContext resolve(String contextType, String contextId) {
      if (WorkspaceCustomizationContext.TYPE.equals(contextType)) {
        return getWorkspace().getObject(ObjectType.WINDOW, contextId);
      } else {
        return customizationContextResolvers.resolve(contextType, contextId);
      }
    }
  };

  public ModelImpl(ChromatticSession session, ContentManagerRegistry contentManagers, CustomizationContextProviderRegistry customizationContextResolvers) {

    //
    this.session = session;
    this.contentManagers = contentManagers;
    this.customizationContextResolvers = customizationContextResolvers;
    this.contentManager = new ContentManagerImpl(contentManagers, customizationContextResolvers, session);

    //
    session.addLifeCycleListener(contextualizer);
  }

  public Workspace getWorkspace() {
    return getWorkspaceImpl();
  }

  private WorkspaceImpl getWorkspaceImpl() {
    if (workspace == null) {
      workspace = session.findByPath(WorkspaceImpl.class, "workspace");
      if (workspace == null) {
        workspace = session.insert(WorkspaceImpl.class, "workspace");
      }
    }
    return workspace;
  }

  public ContentManager getContentManager() {
    return contentManager;
  }

  public void save() {
    session.save();
  }

  public void close() {
    session.close();
  }

  private final LifeCycleListener<Object> contextualizer = new LifeCycleListener<Object>() {
    public void created(Object o) {
      inject(o, false);
    }
    public void loaded(Object o) {
      inject(o, true);
    }
    public void persisted(Object o) {
      inject(o, true);
    }
    public void removed(Object o) {
    }
  };

  public <O extends WorkspaceObject> Iterator<O> findObject(ObjectType<O> type, String statement) {
    Query q = session.createQuery(QueryLanguage.SQL, statement);
    return q.execute(type.getJavaType());
  }

  public String getPath(Object o) {
    return session.getPath(o);
  }

  private void inject(Object o, boolean persistent) {
    if (o instanceof UIWindowImpl) {
      ((UIWindowImpl)o).setContentManager(contentManager);
    } else if (o instanceof ContextSpecialization) {
        ((ContextSpecialization)o).setCustomizationContextResolver(customizationContextResolver);
    } else if (o instanceof WorkspaceClone && persistent) {
      ((WorkspaceClone)o).registry = contentManagers;
    }
  }
}
