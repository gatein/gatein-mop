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
package org.gatein.mop.core.api.workspace.content;

import org.gatein.mop.api.content.Customization;
import org.gatein.mop.api.content.CustomizationContext;
import org.gatein.mop.api.content.customization.CustomizationMode;
import org.gatein.mop.api.workspace.WorkspaceCustomizationContext;
import org.gatein.mop.core.api.content.CustomizationContextComparator;
import org.gatein.mop.core.api.workspace.content.portlet.PortletPreferencesState;
import org.chromattic.api.annotations.OneToOne;
import org.chromattic.api.annotations.MappedBy;
import org.chromattic.api.annotations.Create;

import java.util.Set;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Map;
import java.util.LinkedHashSet;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public abstract class AbstractCustomization implements Customization<Object> {

  /** . */
  protected static final CustomizationContextComparator comparator;

  static {
    CustomizationContextComparator _comparator = new CustomizationContextComparator(
      WorkspaceCustomizationContext.TYPE
    );

    //
    comparator = _comparator;
  }

  @OneToOne
  @MappedBy("state")
  public abstract CustomizationState getContentState();

  public abstract void setContentState(CustomizationState state);

  @OneToOne
  @MappedBy("contexttypes")
  abstract ContextTypeContainer getContextTypes();

  @Create
  abstract ContextType create();

  @Create
  abstract ContextSpecialization create2();

  @Create
  public abstract PortletPreferencesState createPS();

  public abstract CustomizationContext getCustomizationContext();

  public abstract AbstractCustomization getParentCustomization();


  public Object getState() {

    CustomizationState state = getContentState();
    if (state != null) {
      return getContentState().getPayload();
    } else {
      AbstractCustomization parent = getParentCustomization();
      if (parent != null) {
        return parent.getState();
      } else {
        return null;
      }
    }

  }

  public void setState(Object state) {
    if (state == null) {
      setContentState(null);
    } else {
      CustomizationState contentState = getContentState();

      //
      if (contentState == null) {
        contentState = createPS();
        setContentState(contentState);
      }

      //
      contentState.setPayload(state);
    }
  }

  public void destroy() {
    throw new UnsupportedOperationException("todo");
  }

  public Customization<Object> getCustomization(Set<CustomizationContext> contexts) {
    return get(contexts, false);
  }

  public Customization<Object> customize(CustomizationMode mode, Collection<CustomizationContext> contexts) {
    if (mode == null) {
      throw new NullPointerException();
    }
    if (contexts == null) {
      throw new NullPointerException();
    }
    if (mode == CustomizationMode.CLONE) {
      throw new UnsupportedOperationException("todo : support cloning");
    }
    return get(contexts, true);
  }

  public Set<CustomizationContext> getContexts() {
    //
    AbstractCustomization current = this;
    LinkedHashSet<CustomizationContext> contexts = new LinkedHashSet<CustomizationContext>();

    //
    while (true) {
      CustomizationContext currentContext = current.getCustomizationContext();
      if (currentContext == null) {
        throw new IllegalStateException("Could not resolve customization context for customization " +  this);
      }

      //
      contexts.add(currentContext);

      //
      AbstractCustomization parent = current.getParentCustomization();
      if (parent != null) {
        current = parent;
      } else {
        break;
      }
    }

    //
    return contexts;
  }

  //

  protected final Customization<Object> get(Collection<CustomizationContext> contexts, boolean create) {

    // The sorted contexts
    TreeSet<CustomizationContext> sortedContexts = new TreeSet<CustomizationContext>(comparator);

    // Contexts up to this node
    Set<CustomizationContext> existingContexts = getContexts();

    // Add all existing contexts
    sortedContexts.addAll(existingContexts);

    // Sort everything and check consistency
    sortedContexts.addAll(contexts);

    // Remove existing contexts
    sortedContexts.removeAll(existingContexts);

    //
    return get(sortedContexts.iterator(), create);
  }

  protected final Customization<Object> get(Iterator<CustomizationContext> contexts, boolean create) {
    if (contexts.hasNext()) {
      CustomizationContext context = contexts.next();
      String type = context.getContextType();
      ContextTypeContainer typeContainer = getContextTypes();
      Map<String, ContextType> contextTypes = typeContainer.getContextTypes();
      ContextType tmp = contextTypes.get(type);
      if (tmp == null) {
        if (create) {
          tmp = create();
          contextTypes.put(type, tmp);
        } else {
          return null;
        }
      }
      Map<String, ContextSpecialization> c = tmp.getSpecializations();
      String id = context.getContextId();
      ContextSpecialization blah = c.get(id);

      //
      if (blah != null || !create) {
        return blah;
      }

      //
      blah = create2();
      c.put(id, blah);

      //
      return blah.get(contexts, create);
    } else {
      return this;
    }
  }
}
