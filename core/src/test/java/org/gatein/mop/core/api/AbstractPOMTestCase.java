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

import junit.framework.TestCase;

import java.util.Collections;

import org.gatein.mop.core.content.portlet.Preference;
import org.gatein.mop.core.content.portlet.Preferences;
import org.gatein.mop.core.content.portlet.PortletContentProvider;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public abstract class AbstractPOMTestCase extends TestCase {

  /** . */
  protected MOPService pomService;

  @Override
  protected void setUp() throws Exception {
    Preferences weatherPreferences = new Preferences(Collections.<String, Preference>singletonMap(
      "zip", new Preference("zip", Collections.singletonList("marseille"), false)));
    PortletContentProvider portletContentProvider = new PortletContentProvider();
    portletContentProvider.addPortletDefinition("WeatherPortlet", weatherPreferences);

    //
    MOPService pomService = new MOPService();

    //
    pomService.start();

    // Not needed for now
    // pomService.getContentManagerRegistry().register(Preferences.CONTENT_TYPE, portletContentProvider);

    //
    this.pomService = pomService;
  }

}
