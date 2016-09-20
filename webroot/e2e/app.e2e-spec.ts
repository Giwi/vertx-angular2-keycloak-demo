import { DemouiPage } from './app.po';

describe('demoui App', function() {
  let page: DemouiPage;

  beforeEach(() => {
    page = new DemouiPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
