import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SidenavChildComponent } from './sidenav-child.component';

describe('SidenavChildComponent', () => {
  let component: SidenavChildComponent;
  let fixture: ComponentFixture<SidenavChildComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SidenavChildComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SidenavChildComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
