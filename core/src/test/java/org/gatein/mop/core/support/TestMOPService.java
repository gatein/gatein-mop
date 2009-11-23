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
package org.gatein.mop.core.support;

import org.chromattic.api.ChromatticBuilder;
import org.gatein.mop.core.api.MOPService;
import org.gatein.mop.core.api.content.ContentManagerRegistry;
import org.gatein.mop.core.support.content.gadget.Gadget;
import org.gatein.mop.core.support.content.gadget.GadgetContentProvider;
import org.gatein.mop.core.support.content.gadget.GadgetState;
import org.gatein.mop.core.support.content.portlet.PortletContentProvider;
import org.gatein.mop.core.support.content.portlet.PortletPreferenceState;
import org.gatein.mop.core.support.content.portlet.PortletPreferencesState;
import org.gatein.mop.core.support.content.portlet.Preferences;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class TestMOPService extends MOPService
{

   @Override
   protected void configure(ChromatticBuilder builder)
   {
      builder.add(PortletPreferencesState.class);
      builder.add(PortletPreferenceState.class);

      //
      builder.add(GadgetState.class);
   }

   @Override
   protected void configure(ContentManagerRegistry registry)
   {
      registry.register(Preferences.CONTENT_TYPE, new PortletContentProvider());
      registry.register(Gadget.CONTENT_TYPE, new GadgetContentProvider());
   }
}
