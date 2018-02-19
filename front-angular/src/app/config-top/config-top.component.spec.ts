import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfigTopComponent } from './config-top.component';

describe('ConfigTopComponent', () => {
  let component: ConfigTopComponent;
  let fixture: ComponentFixture<ConfigTopComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfigTopComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfigTopComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
