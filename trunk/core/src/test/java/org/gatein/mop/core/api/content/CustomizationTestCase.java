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

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public abstract class CustomizationTestCase extends AbstractPOMTestCase
{

/*
  public void tesGetPersistedCustomization() {
    POM model = pomService.getModel();
    ContentManager manager = model.getContentManager();
    Content content = manager.getContent(Preferences.CONTENT_TYPE, "NonExistingPortlet", FetchCondition.PERSISTED);
    assertNull(content);
  }

  public void testGetNonExistingState() {
    POM model = pomService.getModel();
    ContentManager manager = model.getContentManager();
    Content content = manager.getContent(Preferences.CONTENT_TYPE, "NonExistingPortlet", FetchCondition.ALWAYS);
    assertNotNull(content);
  }

  public void testGetResolvableState() {
    POM model = pomService.getModel();
    ContentManager manager = model.getContentManager();
    Content content = manager.getContent(Preferences.CONTENT_TYPE, "WeatherPortlet", FetchCondition.RESOLVABLE);
    assertNotNull(content);
  }

  public void testCustomize() {
    POM model = pomService.getModel();
    ContentManager manager = model.getContentManager();
    Content content = manager.getContent(Preferences.CONTENT_TYPE, "NonExistingPortlet", FetchCondition.ALWAYS);
    Set<CustomizationContext> contexts = Collections.<CustomizationContext>singleton(model.getWorkspace());

    //
    Customization rootCustomization = content.getCustomization();
    assertNull(content.getCustomization().getCustomization(contexts));
    assertNotNull(rootCustomization);

    //
    Customization workspaceCustomization = rootCustomization.customize(CustomizationMode.CLONE, contexts);
    assertNotNull(workspaceCustomization);
    assertSame(content.getCustomization().getCustomization(contexts), workspaceCustomization);
  }

  public void testDestroyDescendantCustomization() {
    POM model = pomService.getModel();
    ContentManager manager = model.getContentManager();
    Content content = manager.getContent(Preferences.CONTENT_TYPE, "WeatherPortlet", FetchCondition.RESOLVABLE);
    Set<CustomizationContext> contexts = Collections.<CustomizationContext>singleton(model.getWorkspace());
    Customization rootCustomization = content.getCustomization();
    Customization workspaceCustomization = rootCustomization.customize(CustomizationMode.CLONE, contexts);
    workspaceCustomization.destroy();
    assertNull(rootCustomization.getCustomization(contexts));
  }

  public void testCannotDestroyRootCustomization() {
    POM model = pomService.getModel();
    ContentManager manager = model.getContentManager();
    Content content = manager.getContent(Preferences.CONTENT_TYPE, "WeatherPortlet", FetchCondition.RESOLVABLE);
    Customization rootCustomization = content.getCustomization();
    try {
      rootCustomization.destroy();
      fail();
    }
    catch (IllegalStateException ignore) {
    }
  }
*/

}
