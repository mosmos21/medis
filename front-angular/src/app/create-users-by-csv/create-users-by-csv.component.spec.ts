import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateUsersByCsvComponent } from './create-users-by-csv.component';

describe('CreateUsersByCsvComponent', () => {
  let component: CreateUsersByCsvComponent;
  let fixture: ComponentFixture<CreateUsersByCsvComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateUsersByCsvComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateUsersByCsvComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
