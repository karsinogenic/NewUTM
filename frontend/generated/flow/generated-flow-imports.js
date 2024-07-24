import { injectGlobalCss } from 'Frontend/generated/jar-resources/theme-util.js';

import { css, unsafeCSS, registerStyles } from '@vaadin/vaadin-themable-mixin';
import $cssFromFile_0 from 'Frontend/themes/myapp/bootstrap_5.3.2/bootstrap.min.css?inline';

injectGlobalCss($cssFromFile_0.toString(), 'CSSImport end', document);
import $cssFromFile_1 from 'Frontend/themes/myapp/new-main-layout.css?inline';
const $css_1 = typeof $cssFromFile_1  === 'string' ? unsafeCSS($cssFromFile_1) : $cssFromFile_1;
registerStyles('vaadin-app-layout', $css_1, {moduleId: 'flow_css_mod_1'});
import '@vaadin/polymer-legacy-adapter/style-modules.js';
import '@vaadin/login/theme/lumo/vaadin-login-form.js';
import '@vaadin/vertical-layout/theme/lumo/vaadin-vertical-layout.js';
import 'Frontend/generated/jar-resources/flow-component-renderer.js';
import '@vaadin/combo-box/theme/lumo/vaadin-combo-box.js';
import 'Frontend/generated/jar-resources/comboBoxConnector.js';
import '@vaadin/side-nav/theme/lumo/vaadin-side-nav.js';
import 'Frontend/generated/jar-resources/vaadin-grid-flow-selection-column.js';
import '@vaadin/grid/theme/lumo/vaadin-grid-column.js';
import '@vaadin/app-layout/theme/lumo/vaadin-app-layout.js';
import '@vaadin/tooltip/theme/lumo/vaadin-tooltip.js';
import '@vaadin/tabs/theme/lumo/vaadin-tab.js';
import '@vaadin/button/theme/lumo/vaadin-button.js';
import 'Frontend/generated/jar-resources/buttonFunctions.js';
import 'Frontend/generated/jar-resources/menubarConnector.js';
import '@vaadin/menu-bar/theme/lumo/vaadin-menu-bar.js';
import '@vaadin/form-layout/theme/lumo/vaadin-form-layout.js';
import '@vaadin/dialog/theme/lumo/vaadin-dialog.js';
import '@vaadin/horizontal-layout/theme/lumo/vaadin-horizontal-layout.js';
import '@vaadin/grid/theme/lumo/vaadin-grid-column-group.js';
import '@vaadin/password-field/theme/lumo/vaadin-password-field.js';
import '@vaadin/icon/theme/lumo/vaadin-icon.js';
import '@vaadin/side-nav/theme/lumo/vaadin-side-nav-item.js';
import '@vaadin/context-menu/theme/lumo/vaadin-context-menu.js';
import 'Frontend/generated/jar-resources/contextMenuConnector.js';
import 'Frontend/generated/jar-resources/contextMenuTargetConnector.js';
import '@vaadin/form-layout/theme/lumo/vaadin-form-item.js';
import '@vaadin/multi-select-combo-box/theme/lumo/vaadin-multi-select-combo-box.js';
import '@vaadin/grid/theme/lumo/vaadin-grid.js';
import '@vaadin/grid/theme/lumo/vaadin-grid-sorter.js';
import '@vaadin/checkbox/theme/lumo/vaadin-checkbox.js';
import 'Frontend/generated/jar-resources/gridConnector.ts';
import '@vaadin/text-field/theme/lumo/vaadin-text-field.js';
import '@vaadin/icons/vaadin-iconset.js';
import '@vaadin/date-picker/theme/lumo/vaadin-date-picker.js';
import 'Frontend/generated/jar-resources/datepickerConnector.js';
import '@vaadin/text-area/theme/lumo/vaadin-text-area.js';
import '@vaadin/app-layout/theme/lumo/vaadin-drawer-toggle.js';
import '@vaadin/tabs/theme/lumo/vaadin-tabs.js';
import '@vaadin/avatar/theme/lumo/vaadin-avatar.js';
import '@vaadin/scroller/theme/lumo/vaadin-scroller.js';
import 'Frontend/generated/jar-resources/lit-renderer.ts';
import '@vaadin/notification/theme/lumo/vaadin-notification.js';
import '@vaadin/common-frontend/ConnectionIndicator.js';
import '@vaadin/vaadin-lumo-styles/color-global.js';
import '@vaadin/vaadin-lumo-styles/typography-global.js';
import '@vaadin/vaadin-lumo-styles/sizing.js';
import '@vaadin/vaadin-lumo-styles/spacing.js';
import '@vaadin/vaadin-lumo-styles/style.js';
import '@vaadin/vaadin-lumo-styles/vaadin-iconset.js';

const loadOnDemand = (key) => {
  const pending = [];
  if (key === 'fdc2c77768499f02cbebbb1e5e42cc00a02fbf7527a111968ad72305c0926a85') {
    pending.push(import('./chunks/chunk-65114c6affafe1f38983eabee714ac9e3c930fb080653952ddc01f9b0bfb7c01.js'));
  }
  if (key === 'ace941d9e2ccc312854dc067a4cddf3e08493d6d893222b73420ca71fdc576a6') {
    pending.push(import('./chunks/chunk-da19783e8b8348046181a8c9c4b559cb1cdcc8cac390373a6d1137f913418be4.js'));
  }
  if (key === '204ebc96d30226952449e58fa795e0131643daf9519130252fa5e8f3666b5087') {
    pending.push(import('./chunks/chunk-c19a7da08f430dcb174a726082c7e28d423dbae89025a4958e566ef622a3999f.js'));
  }
  if (key === 'fe5fb3717689afe9f65335956f92a908fcc96571a7dff7b9049f0eaacc74c6cf') {
    pending.push(import('./chunks/chunk-bb120c9ed101641780df564034c0762d6a6eafaf6fdd748b88bd457b160bb416.js'));
  }
  if (key === 'ecd87012d4c46bd90522fee6ebd3b878b4d348f06f3eef8c0f9244aa0ec4f889') {
    pending.push(import('./chunks/chunk-da19783e8b8348046181a8c9c4b559cb1cdcc8cac390373a6d1137f913418be4.js'));
  }
  if (key === '885e2cdb086cc30cb62dc5df8304e6a9ddbb6ab1a501ce34ee151329c5e2ae3f') {
    pending.push(import('./chunks/chunk-da19783e8b8348046181a8c9c4b559cb1cdcc8cac390373a6d1137f913418be4.js'));
  }
  if (key === 'f0ef981ce98b2a8e55d37bf0d473cdf374cd0a0bfcbf69d45b6887ce9ff42856') {
    pending.push(import('./chunks/chunk-1c8594f6b4c849a06f5c21b3609b4ce8f9ce7062e4829bba30b4aac6d717059b.js'));
  }
  if (key === '21d5b96fe2d636d00185e6750dafac01abaeff07fe131279b72f419435f34fbc') {
    pending.push(import('./chunks/chunk-bb120c9ed101641780df564034c0762d6a6eafaf6fdd748b88bd457b160bb416.js'));
  }
  if (key === '1110baf499e29f47ad6453020dc7ccc00522c147015e52f152a3b82652e5722a') {
    pending.push(import('./chunks/chunk-bb120c9ed101641780df564034c0762d6a6eafaf6fdd748b88bd457b160bb416.js'));
  }
  if (key === '720d3ab1cb53b0cb6ee8df2e351763e33962e5819f2d77de8d9039282bff6614') {
    pending.push(import('./chunks/chunk-1c8594f6b4c849a06f5c21b3609b4ce8f9ce7062e4829bba30b4aac6d717059b.js'));
  }
  if (key === 'ffc4a058a138ba175dbc180498ef38e008ba54c6966de3b2f593c3cda8f3b104') {
    pending.push(import('./chunks/chunk-c19a7da08f430dcb174a726082c7e28d423dbae89025a4958e566ef622a3999f.js'));
  }
  if (key === 'b13f666f08384834f0cd801798ce4f4ec019ab5269475cb19f7e403f53bdf5cd') {
    pending.push(import('./chunks/chunk-8c3284ea48bc9f01c4e04d7b35f182607f31e940c5687ce2d747687c928a5d36.js'));
  }
  if (key === '10b897749a0899653ecfd86f46ccb659fa23a8a1cafae08e51c3af99369d6fda') {
    pending.push(import('./chunks/chunk-bb120c9ed101641780df564034c0762d6a6eafaf6fdd748b88bd457b160bb416.js'));
  }
  if (key === '733290f787bd5c5816c1f4060c83c31f1c66a019550119031d834c9f4a8b2a4b') {
    pending.push(import('./chunks/chunk-8c3284ea48bc9f01c4e04d7b35f182607f31e940c5687ce2d747687c928a5d36.js'));
  }
  if (key === '7902fb1ccb0dab9ea3c5cf4fae4dc9205c86560db794eecee781ddc6b29edc74') {
    pending.push(import('./chunks/chunk-1c8594f6b4c849a06f5c21b3609b4ce8f9ce7062e4829bba30b4aac6d717059b.js'));
  }
  if (key === 'a62fc4bf580c3457e6bd05c6c11a9b34497f7b05c5d89e5bc9f0f00eba06b4ce') {
    pending.push(import('./chunks/chunk-bb120c9ed101641780df564034c0762d6a6eafaf6fdd748b88bd457b160bb416.js'));
  }
  if (key === 'fe4dc39a71156858774aa543c1c6a49ae931a1ff84675ef17985799d96142fc7') {
    pending.push(import('./chunks/chunk-1c8594f6b4c849a06f5c21b3609b4ce8f9ce7062e4829bba30b4aac6d717059b.js'));
  }
  if (key === '6dc49c5a199096a82435e89fc750ffb820668e6d89322af1562046ce4b20b889') {
    pending.push(import('./chunks/chunk-bb120c9ed101641780df564034c0762d6a6eafaf6fdd748b88bd457b160bb416.js'));
  }
  if (key === 'f99eda91d2af19746b52dcb35dbee554c9dd0b1b35a34ae17a97f6cc9f46a59a') {
    pending.push(import('./chunks/chunk-bb120c9ed101641780df564034c0762d6a6eafaf6fdd748b88bd457b160bb416.js'));
  }
  if (key === '4ac5a22056da41f3c58c81bb47861485661c0d2ec85f7188308e435ab1d6631e') {
    pending.push(import('./chunks/chunk-65114c6affafe1f38983eabee714ac9e3c930fb080653952ddc01f9b0bfb7c01.js'));
  }
  if (key === 'd349920c26284741b4ab910be0668ed94f87802b486553915572c8742ad03265') {
    pending.push(import('./chunks/chunk-da19783e8b8348046181a8c9c4b559cb1cdcc8cac390373a6d1137f913418be4.js'));
  }
  if (key === '5de179f9c7a9614208ea341001ec74d7a1019ea34ec0bd172b687fbf04f06af8') {
    pending.push(import('./chunks/chunk-e97832f525f3124ee459c289e09db7f6209612a59a5669ac94bdd3478149aa5f.js'));
  }
  if (key === 'fcc44a318a4ca723617b42ae15b29c98e99baaeffdfd970e5d97f8834410c5dd') {
    pending.push(import('./chunks/chunk-1c8594f6b4c849a06f5c21b3609b4ce8f9ce7062e4829bba30b4aac6d717059b.js'));
  }
  return Promise.all(pending);
}

window.Vaadin = window.Vaadin || {};
window.Vaadin.Flow = window.Vaadin.Flow || {};
window.Vaadin.Flow.loadOnDemand = loadOnDemand;
window.Vaadin.Flow.resetFocus = () => {
 let ae=document.activeElement;
 while(ae&&ae.shadowRoot) ae = ae.shadowRoot.activeElement;
 return !ae || ae.blur() || ae.focus() || true;
}