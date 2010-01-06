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

import org.chromattic.api.annotations.PrimaryType;
import org.chromattic.api.annotations.Name;
import org.chromattic.api.annotations.RelatedMappedBy;
import org.chromattic.api.annotations.OneToMany;
import org.chromattic.api.RelationshipType;
import org.gatein.mop.api.content.CustomizationContext;
import org.gatein.mop.api.workspace.WorkspaceCustomizationContext;

import java.util.Set;
import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
@PrimaryType(name = "mop:workspaceclone")
public abstract class WorkspaceClone extends WorkspaceCustomization
{

   public Set<CustomizationContext> getContexts()
   {
      return Collections.emptySet();
   }

   @Name
   public abstract String getFooName();

   @OneToMany(type = RelationshipType.PATH)
   @RelatedMappedBy("mop:customization")
   public abstract Collection<WorkspaceSpecialization> getSpecializations();

   //

   public String getName()
   {
      CustomizationContext customizationContext = getContext();
      if (customizationContext instanceof WorkspaceCustomizationContext)
      {
         return getFooName();
      }
      else
      {
         return null;
      }
   }

   public AbstractCustomization getParent()
   {
      return null;
   }

   public void destroy()
   {
      // Get specializations (clone for now because of bug with concurrent modif in chromattic)
      Collection<WorkspaceSpecialization> specializations = new ArrayList<WorkspaceSpecialization>(getSpecializations());

      //
      for (WorkspaceSpecialization specialization : specializations)
      {
         // Update the specialization with its virtual state
         Object state = specialization.getVirtualState();
         specialization.setState(state);

         // Clear ref
         specialization.setCustomization(null);
      }

      //
      doDestroy();
   }
}
