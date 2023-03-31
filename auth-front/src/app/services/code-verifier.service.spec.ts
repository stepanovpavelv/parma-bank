import { TestBed } from '@angular/core/testing';

import { CodeVerifierService } from './code-verifier.service';

describe('CodeVerifierService', () => {
  let service: CodeVerifierService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CodeVerifierService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
