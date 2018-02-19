import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfigNotificationComponent } from './config-notification.component';

describe('ConfigNotificationComponent', () => {
  let component: ConfigNotificationComponent;
  let fixture: ComponentFixture<ConfigNotificationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfigNotificationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfigNotificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
