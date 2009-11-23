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
import org.gatein.mop.api.content.ContentType;
import org.gatein.mop.api.workspace.WorkspaceCustomizationContext;
import org.gatein.mop.core.api.content.CustomizationContextComparator;
import org.gatein.mop.core.api.content.ContentManagerRegistry;
import org.gatein.mop.spi.content.ContentProvider;
import org.gatein.mop.spi.content.StateContainer;
import org.chromattic.api.annotations.OneToOne;
import org.chromattic.api.annotations.MappedBy;
import org.chromattic.api.annotations.Create;
import org.chromattic.api.annotations.Id;
import org.chromattic.api.ChromatticSession;

import java.util.Set;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Map;
import java.util.LinkedHashSet;
import java.util.Arrays;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public abstract class AbstractCustomization implements Customization<Object>, StateContainer
{

   /** . */
   protected static final CustomizationContextComparator comparator;

   static
   {
      CustomizationContextComparator _comparator = new CustomizationContextComparator(
         WorkspaceCustomizationContext.TYPE
      );

      //
      comparator = _comparator;
   }

   /** . */
   public ContentManagerRegistry registry;

   /** . */
   public ChromatticSession session;

   @Id
   public abstract String getId();

   @OneToOne
   @MappedBy("contexttypes")
   abstract ContextTypeContainer getContextTypes();

   @Create
   abstract ContextType create();

   @Create
   abstract ContextSpecialization createContextSpecialization();

   public abstract CustomizationContext getContext();

   public abstract AbstractCustomization getParent();

   public Object getVirtualState()
   {

      ContentType contentType = getType();

      String mimeType = contentType.getMimeType();

      ContentProvider contentProvider = registry.providers.get(mimeType).getProvider();

      //
      Object childPayload = contentProvider.getState(this);

      //
      Object parentPayload = null;
      AbstractCustomization parent = getParent();
      if (parent != null)
      {
         parentPayload = parent.getVirtualState();
      }

      //
      if (parentPayload != null)
      {
         if (childPayload != null)
         {
            return contentProvider.combine(Arrays.asList(parentPayload, childPayload));
         }
         else
         {
            return parentPayload;
         }
      }
      else
      {
         return childPayload;
      }
   }

   public Object getState()
   {
      ContentType contentType = getType();
      String mimeType = contentType.getMimeType();
      ContentProvider contentProvider = registry.providers.get(mimeType).getProvider();
      return contentProvider.getState(this);
   }

   public void setState(Object state)
   {
      ContentType contentType = getType();
      String mimeType = contentType.getMimeType();
      ContentProvider contentProvider = registry.providers.get(mimeType).getProvider();
      contentProvider.setState(this, state);
   }

   public Customization<Object> getCustomization(Set<CustomizationContext> contexts)
   {
      return get(contexts, false);
   }

   public Customization<Object> customize(Collection<CustomizationContext> contexts)
   {
      if (contexts == null)
      {
         throw new NullPointerException();
      }
      return get(contexts, true);
   }

   public Set<CustomizationContext> getContexts()
   {
      //
      AbstractCustomization current = this;
      LinkedHashSet<CustomizationContext> contexts = new LinkedHashSet<CustomizationContext>();

      //
      while (true)
      {
         CustomizationContext currentContext = current.getContext();
         if (currentContext == null)
         {
            throw new IllegalStateException("Could not resolve customization context for customization " + this);
         }

         //
         contexts.add(currentContext);

         //
         AbstractCustomization parent = current.getParent();
         if (parent != null)
         {
            current = parent;
         }
         else
         {
            break;
         }
      }

      //
      return contexts;
   }

   //

   protected final Customization<Object> get(Collection<CustomizationContext> contexts, boolean create)
   {

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

   protected final Customization<Object> get(Iterator<CustomizationContext> contexts, boolean create)
   {
      if (contexts.hasNext())
      {
         CustomizationContext context = contexts.next();
         String type = context.getContextType();
         ContextTypeContainer typeContainer = getContextTypes();
         Map<String, ContextType> contextTypes = typeContainer.getContextTypes();
         ContextType tmp = contextTypes.get(type);
         if (tmp == null)
         {
            if (create)
            {
               tmp = create();
               contextTypes.put(type, tmp);
            }
            else
            {
               return null;
            }
         }
         Map<String, ContextSpecialization> c = tmp.getSpecializations();
         String id = context.getContextId();
         ContextSpecialization blah = c.get(id);

         //
         if (blah != null || !create)
         {
            return blah;
         }

         //
         blah = createContextSpecialization();
         c.put(id, blah);

         //
         return blah.get(contexts, create);
      }
      else
      {
         return this;
      }
   }
}
