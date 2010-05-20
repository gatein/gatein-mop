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
package org.gatein.mop.core.api.content;

import org.gatein.mop.core.api.AbstractPOMTestCase;
import org.gatein.mop.api.content.ContentType;
import org.gatein.mop.core.support.content.portlet.Preferences;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public abstract class BiltoTestCase extends AbstractPOMTestCase
{

   /** . */
   private final ContentType portletContentType = Preferences.CONTENT_TYPE;

/*
  public void testBar() throws Exception {

    POM model = pomService.getModel();

    //
    ContentManager manager = model.getContentManager();
    assertNotNull(manager);

    Content content = manager.getContent(portletContentType, "WeatherPortlet", FetchCondition.ALWAYS);
    assertNotNull(content);

    Customization customization = content.getCustomization();

    assertNotNull(customization);

    Object state = customization.getState();
    assertNotNull(state);

    Workspace workspace = model.getWorkspace();
    Set<CustomizationContext> contexts = Collections.singleton((CustomizationContext)workspace);
    Customization wcustomization = customization.customize(CustomizationMode.SPECIALIZE, contexts);
    assertNotNull(wcustomization);

    Set<CustomizationContext> wcontexts = wcustomization.getContexts();
    assertEquals(1, wcontexts.size());
    assertTrue(wcontexts.contains(workspace));

    Site site = workspace.addSite(ObjectType.PORTAL_SITE, "site");

    Page root = site.getRootPage();
    UIContainer layout = root.getLayout();
    UIWindow window = layout.addComponent(ObjectType.WINDOW, "window");

    Collection<CustomizationContext> wincontexts = Arrays.asList((CustomizationContext)workspace, site, root, window);

    Customization wincust = wcustomization.customize(CustomizationMode.SPECIALIZE, wincontexts);

    assertNotNull(wincust);

    Set<CustomizationContext> allc = wincust.getContexts();
    assertEquals(4, allc.size());
    assertTrue(allc.contains(workspace));
    assertTrue(allc.contains(site));
    assertTrue(allc.contains(root));
    assertTrue(allc.contains(window));




*/
/*
    ContentManager contentManager = model.getContentManager();
    Content content = contentManager.getContent(ContentType.create("portlet"), "WeatherPortlet");
    assertNotNull(content);

    //
    Workspace workspace = model.getWorkspace();
    Portal portal = (Portal)workspace.createSite("portal", SiteType.PORTAL);
    Page rootPage = portal.getRootPage();
    UIContainer layout = rootPage.getLayout();
    UIWindow window = layout.addWindow("window");
    window.setContent(content);

    //
    Content other = window.getContent();
    assertNotNull(other);

    //
    ContentCustomization customization = content.getCustomization();
*/
/*
  }

  public void testWindowContent() throws Exception {
    POM model = pomService.getModel();
    Content content = model.getContentManager().getContent(Preferences.CONTENT_TYPE, "WeatherPortlet", FetchCondition.ALWAYS);
    assertNotNull(content);
    Workspace workpace = model.getWorkspace();
    Site site = workpace.addSite(ObjectType.PORTAL_SITE, "portal");
    UIWindow window = site.getRootPage().getLayout().addComponent(ObjectType.WINDOW, "window");
    window.setContent(content);

//    model.save();
//    model.close();

    //
//    model = pomService.getModel();
    window = (UIWindow)model.getWorkspace().getSite(ObjectType.PORTAL_SITE, "portal").getRootPage().getLayout().getComponent("window");
    content = window.getContent();
    assertNotNull(content);
    assertEquals("WeatherPortlet", content.getId());
    assertEquals(Preferences.CONTENT_TYPE, content.getType());
  }

*/
/*
  public void testNoContentProvider() throws Exception {

  }
*/
/*

  public void testFooo() throws Exception {
*/
/*
    POM model = pomService.getModel();
    Workspace workspace = model.getWorkspace();
    CustomizationContainer container = model.getContainer();

    //
    assertNotNull(container.getCustomizations());
    Customization customization = container.add("foo");
    assertNotNull(customization);
*/
/*

    //
    // workspace.setCustomization(customization);

    //
//    session.save();

    // customization.customize(CustomizationMode.SPECIALIZE, );


  }
*/
}
