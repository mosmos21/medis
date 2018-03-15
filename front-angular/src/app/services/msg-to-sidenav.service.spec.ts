import { TestBed, inject } from '@angular/core/testing';

import { MsgToSidenavService } from './msg-to-sidenav.service';

describe('MsgToSidenavService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [MsgToSidenavService]
    });
  });

  it('should be created', inject([MsgToSidenavService], (service: MsgToSidenavService) => {
    expect(service).toBeTruthy();
  }));
});
