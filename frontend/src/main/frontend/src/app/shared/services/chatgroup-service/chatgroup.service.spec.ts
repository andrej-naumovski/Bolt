import { TestBed, inject } from '@angular/core/testing';

import { ChatgroupService } from './chatgroup.service';

describe('ChatgroupService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ChatgroupService]
    });
  });

  it('should ...', inject([ChatgroupService], (service: ChatgroupService) => {
    expect(service).toBeTruthy();
  }));
});
