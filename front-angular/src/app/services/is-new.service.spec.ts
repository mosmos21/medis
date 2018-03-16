import { TestBed, inject } from '@angular/core/testing';

import { IsNewService } from './is-new.service';

describe('IsNewService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [IsNewService]
    });
  });

  it('should be created', inject([IsNewService], (service: IsNewService) => {
    expect(service).toBeTruthy();
  }));
});
