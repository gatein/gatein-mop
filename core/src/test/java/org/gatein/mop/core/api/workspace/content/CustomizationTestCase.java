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
import org.gatein.mop.core.support.content.portlet.Preferences;
import org.gatein.mop.core.support.content.portlet.PreferencesBuilder;
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
  private final Preferences marseille = new PreferencesBuilder().add("city", "marseille").add("temperature", "celsius").build();

  /** . */
  private final Preferences paris = new PreferencesBuilder().add("city", "paris").build();

  /** . */
  private final Preferences parisCelsius = new PreferencesBuilder().add("city", "paris").add("temperature", "celsius").build();

  /** . */
  private final Preferences parisFarenheit = new PreferencesBuilder().add("city", "paris").add("temperature", "farenheit").build();

  public void testVirtualCustomization() {
    Workspace workspace = pomService.getModel().getWorkspace();
    Customization<Preferences> customization1 = workspace.customize("marseille", Preferences.CONTENT_TYPE, "WeatherPortlet", marseille);
    Customization<Preferences> customization2 = workspace.customize("paris", customization1);

    //
    customization2.setState(paris);
    assertEquals(paris, customization2.getState());
    assertEquals(parisCelsius, customization2.getVirtualState());

    //
    customization2.setState(parisFarenheit);
    assertEquals(parisFarenheit, customization2.getState());
    assertEquals(parisFarenheit, customization2.getVirtualState());

    //
    customization1.setState(customization1.getState().setReadOnly("temperature", true));
    assertEquals(parisFarenheit, customization2.getState());
    assertEquals(parisCelsius, customization2.getVirtualState());
  }

  public void testCustomizeWorkspace() {
    Workspace workspace = pomService.getModel().getWorkspace();
    assertNull(workspace.getCustomization("marseille"));
    Customization<Preferences> customization = workspace.customize("marseille", Preferences.CONTENT_TYPE, "WeatherPortlet", marseille);
    assertNotNull(customization);
    Preferences b = customization.getState();
    assertEquals(marseille, b);
    assertEquals(0, customization.getContexts().size());
    assertEquals("WeatherPortlet", customization.getContentId());
    assertEquals(Preferences.CONTENT_TYPE, customization.getType());
    assertEquals("marseille", workspace.nameOf(customization));
  }

  public void testCustomizeWindow() {
    Workspace workspace = pomService.getModel().getWorkspace();
    Site portal = workspace.addSite(ObjectType.PORTAL_SITE, "portal");
    UIContainer layout = portal.getRootPage().getRootComponent();
    UIWindow window = layout.add(ObjectType.WINDOW, "window");

    //
    Customization<Preferences> customization = window.customize(Preferences.CONTENT_TYPE, "WeatherPortlet", marseille);
    assertSame(customization, window.getCustomization());
    assertEquals(marseille, customization.getVirtualState());
    assertEquals(marseille, customization.getState());

    //
    customization = (Customization<Preferences>)window.getCustomization();
    assertSame(customization, window.getCustomization());
    assertEquals(marseille, customization.getVirtualState());
    assertEquals(marseille, customization.getState());
  }

  public void testCustomizeWindowByCloneWithNoState() {
    Workspace workspace = pomService.getModel().getWorkspace();
    Site portal = workspace.addSite(ObjectType.PORTAL_SITE, "portal");
    UIContainer layout = portal.getRootPage().getRootComponent();
    UIWindow window = layout.add(ObjectType.WINDOW, "window");

    //
    Customization<Preferences> customization = window.customize(Preferences.CONTENT_TYPE, "WeatherPortlet", null);
    assertSame(customization, window.getCustomization());
    assertNull(customization.getState());
  }

  public void testCustomizeWindowBySpecialization() {
    Workspace workspace = pomService.getModel().getWorkspace();
    Site portal = workspace.addSite(ObjectType.PORTAL_SITE, "portal");
    UIContainer layout = portal.getRootPage().getRootComponent();
    UIWindow window = layout.add(ObjectType.WINDOW, "window");

    //
    Customization<Preferences> workspaceCustomization = portal.customize("marseille", Preferences.CONTENT_TYPE, "WeatherPortlet", marseille);
    Customization<Preferences> windowCustomization = window.customize(workspaceCustomization);

    //
    assertNotNull(windowCustomization);
    assertEquals(Arrays.<CustomizationContext>asList(window, portal), new ArrayList<CustomizationContext>(windowCustomization.getContexts()));
    assertSame(windowCustomization, window.getCustomization());
    assertTrue(((Customization)windowCustomization) instanceof WorkspaceSpecialization);

    //
    assertPreferences(null, windowCustomization.getState());
    assertPreferences(marseille, windowCustomization.getVirtualState());
    assertPreferences(marseille, workspaceCustomization.getState());

    //
    workspaceCustomization.setState(paris);
    assertPreferences(paris, windowCustomization.getVirtualState());
    assertPreferences(null, windowCustomization.getState());
    assertPreferences(paris, workspaceCustomization.getState());

    //
    windowCustomization.setState(parisFarenheit);
    assertPreferences(parisFarenheit, windowCustomization.getVirtualState());
    assertPreferences(parisFarenheit, windowCustomization.getState());
    assertPreferences(paris, workspaceCustomization.getState());
  }

  public void testDestroySpecializedCustomization() {
    Workspace workspace = pomService.getModel().getWorkspace();
    Site portal = workspace.addSite(ObjectType.PORTAL_SITE, "portal");
    UIContainer layout = portal.getRootPage().getRootComponent();
    UIWindow window = layout.add(ObjectType.WINDOW, "window");

    //
    Customization<Preferences> workspaceCustomization = portal.customize("marseille", Preferences.CONTENT_TYPE, "WeatherPortlet", marseille);
    Customization<Preferences> windowCustomization = window.customize(workspaceCustomization);

    //
    workspaceCustomization.destroy();

    //
    assertEquals(marseille, windowCustomization.getVirtualState());
    assertEquals(marseille, windowCustomization.getState());
  }

  public void testDestroySpecialization() {
    Workspace workspace = pomService.getModel().getWorkspace();
    Site portal = workspace.addSite(ObjectType.PORTAL_SITE, "portal");
    UIContainer layout = portal.getRootPage().getRootComponent();
    UIWindow window = layout.add(ObjectType.WINDOW, "window");

    //
    Customization<Preferences> workspaceCustomization = portal.customize("marseille", Preferences.CONTENT_TYPE, "WeatherPortlet", marseille);
    Customization<Preferences> windowCustomization = window.customize(workspaceCustomization);

    //
    windowCustomization.destroy();
  }

  private void assertPreferences(Preferences expectedPrefs, Preferences prefs) {
    assertEquals("Was expecting to have prefs " + prefs + " equals to " + expectedPrefs, expectedPrefs, prefs);
  }
}
