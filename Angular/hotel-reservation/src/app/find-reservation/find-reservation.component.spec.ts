import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FindReservationComponent } from './find-reservation.component';

describe('FindReservationComponent', () => {
  let component: FindReservationComponent;
  let fixture: ComponentFixture<FindReservationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FindReservationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FindReservationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
