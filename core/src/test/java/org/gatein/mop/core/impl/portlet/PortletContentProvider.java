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
package org.gatein.mop.core.impl.portlet;

import org.gatein.mop.spi.content.ContentProvider;
import org.gatein.mop.spi.content.GetState;
import org.gatein.mop.core.content.portlet.Preference;
import org.gatein.mop.core.content.portlet.Preferences;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class PortletContentProvider implements ContentProvider<Preferences> {

  /** . */
  private Map<String, PortletDefinition> definitions;

  public PortletContentProvider() {
    definitions = new HashMap<String, PortletDefinition>();
  }

  public void addPortletDefinition(String name, Preferences preferences) {
    definitions.put(name, new PortletDefinition(name, preferences));
  }

  public GetState<Preferences> getState(String contentId) {
    PortletDefinition def = definitions.get(contentId);
    if (def != null) {
      return new GetState<Preferences>(def.getPreferences());
    } else {
      return null;
    }
  }

  public Preferences combine(List<Preferences> states) {
    Map<String, Preference> entries = new HashMap<String, Preference>();

    //
    for (Preferences preferences : states) {
      for (Preference entry : preferences.getEntries()) {
        Preference previous = entries.get(entry.getName());
        if (previous == null || !previous.isReadOnly()) {
          entries.put(entry.getName(), entry);
        }
      }
    }

    //
    return new Preferences(entries);
  }
}
