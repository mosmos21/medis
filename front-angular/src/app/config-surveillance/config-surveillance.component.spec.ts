import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfigSurveillanceComponent } from './config-surveillance.component';

describe('ConfigSurveillanceComponent', () => {
  let component: ConfigSurveillanceComponent;
  let fixture: ComponentFixture<ConfigSurveillanceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfigSurveillanceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfigSurveillanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
