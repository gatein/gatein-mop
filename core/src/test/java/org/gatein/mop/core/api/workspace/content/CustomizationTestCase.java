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

import org.gatein.mop.core.api.AbstractPOMTestCase;
import org.gatein.mop.core.content.portlet.Preferences;
import org.gatein.mop.core.content.portlet.PreferencesBuilder;
import org.gatein.mop.api.workspace.Workspace;
import org.gatein.mop.api.workspace.ObjectType;
import org.gatein.mop.api.workspace.Site;
import org.gatein.mop.api.workspace.ui.UIContainer;
import org.gatein.mop.api.workspace.ui.UIWindow;
import org.gatein.mop.api.content.Customization;
import org.gatein.mop.api.content.CustomizationContext;

import java.util.Arrays;
import java.util.ArrayList;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class CustomizationTestCase extends AbstractPOMTestCase {

  /** . */
  private final Preferences prefs1 = new PreferencesBuilder().add("foo", "foo1").add("bar", "bar1", true).build();

  /** . */
  private final Preferences prefs2 = new PreferencesBuilder().add("foo", "foo2").add("bar", "bar2", true).build();

  /** . */
  private final Preferences prefs3 = new PreferencesBuilder().add("foo", "foo3").add("bar", "bar3").build();

  public void testCustmizeWorkspace() {
    Workspace workspace = pomService.getModel().getWorkspace();
    assertNull(workspace.getCustomization("marseille"));
    Customization<Preferences> customization = workspace.customize("marseille", Preferences.CONTENT_TYPE, "WeatherPortlet", prefs1);
    assertNotNull(customization);
    Preferences b = customization.getState();
    assertEquals(prefs1, b);
    assertEquals(0, customization.getContexts().size());
    assertEquals("WeatherPortlet", customization.getContentId());
    assertEquals(Preferences.CONTENT_TYPE, customization.getType());
    assertEquals("marseille", customization.getName());
  }

  public void testCustomizeWindow() {
    Workspace workspace = pomService.getModel().getWorkspace();
    Site portal = workspace.addSite(ObjectType.PORTAL_SITE, "portal");
    UIContainer layout = portal.getRootPage().getRootComponent();
    UIWindow window = layout.add(ObjectType.WINDOW, "window");
    Customization<Preferences> customization = window.customize(Preferences.CONTENT_TYPE, "WeatherPortlet", prefs1);
    assertSame(customization, window.getCustomization());
    assertEquals(null, customization.getName());
    assertEquals(prefs1, customization.getState());
  }

  public void testCustomizeWindowByCloneWithNoState() {
    Workspace workspace = pomService.getModel().getWorkspace();
    Site portal = workspace.addSite(ObjectType.PORTAL_SITE, "portal");
    UIContainer layout = portal.getRootPage().getRootComponent();
    UIWindow window = layout.add(ObjectType.WINDOW, "window");
    Customization<Preferences> customization = window.customize(Preferences.CONTENT_TYPE, "WeatherPortlet", null);
    assertSame(customization, window.getCustomization());
    assertEquals(null, customization.getName());
    assertNull(customization.getState());
  }

  public void testCustomizeWindowBySpecialization() {
    Workspace workspace = pomService.getModel().getWorkspace();
    Site portal = workspace.addSite(ObjectType.PORTAL_SITE, "portal");
    Customization<Preferences> workspaceCustomization = portal.customize("marseille", Preferences.CONTENT_TYPE, "WeatherPortlet", prefs1);
    UIContainer layout = portal.getRootPage().getRootComponent();
    UIWindow window = layout.add(ObjectType.WINDOW, "window");
    Customization<Preferences> windowCustomization = window.customize(workspaceCustomization);

    //
    assertNotNull(windowCustomization);
    assertEquals(Arrays.asList(window, portal), new ArrayList<CustomizationContext>(windowCustomization.getContexts()));
    assertSame(windowCustomization, window.getCustomization());
    assertTrue(((Customization)windowCustomization) instanceof WorkspaceSpecialization);
    assertEquals("marseille", windowCustomization.getName());

    //
    assertPreferences(prefs1, windowCustomization.getState());
    assertPreferences(prefs1, workspaceCustomization.getState());

    //
    workspaceCustomization.setState(prefs2);
    assertPreferences(prefs2, windowCustomization.getState());
    assertPreferences(prefs2, workspaceCustomization.getState());

    //
    windowCustomization.setState(prefs3);
    assertPreferences(prefs3, windowCustomization.getState());
    assertPreferences(prefs2, workspaceCustomization.getState());
  }

  public void testDestroySpecializedCustomization() {
    Workspace workspace = pomService.getModel().getWorkspace();
    Site portal = workspace.addSite(ObjectType.PORTAL_SITE, "portal");
    Customization<Preferences> workspaceCustomization = portal.customize("marseille", Preferences.CONTENT_TYPE, "WeatherPortlet", prefs1);
    UIContainer layout = portal.getRootPage().getRootComponent();
    UIWindow window = layout.add(ObjectType.WINDOW, "window");
    Customization<Preferences> windowCustomization = window.customize(workspaceCustomization);

    //
    workspaceCustomization.destroy();
    assertEquals(prefs1, windowCustomization.getState());
  }

/*
  public void testDeleteSpecializedCustomization() {
    Workspace workspace = pomService.getModel().getWorkspace();
    Site portal = workspace.addSite(ObjectType.PORTAL_SITE, "portal");
    Customization<Preferences> workspaceCustomization = portal.customize("marseille", Preferences.CONTENT_TYPE, "WeatherPortlet", prefs1);
    UIContainer layout = portal.getRootPage().getLayout();
    UIWindow window = layout.addComponent(ObjectType.WINDOW, "window");
    Customization<Preferences> windowCustomization = window.customize(workspaceCustomization);

    //
    workspaceCustomization.destroy();
  }
*/

  private void assertPreferences(Preferences expectedPrefs, Preferences prefs) {
    assertEquals("Was expecting to have prefs " + prefs + " equals to " + expectedPrefs, expectedPrefs, prefs);
  }
}
