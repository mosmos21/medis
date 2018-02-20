import { TestBed, inject } from '@angular/core/testing';

import { ConvertDateService } from './convert-date.service';

describe('ConvertDateService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ConvertDateService]
    });
  });

  it('should be created', inject([ConvertDateService], (service: ConvertDateService) => {
    expect(service).toBeTruthy();
  }));
});
