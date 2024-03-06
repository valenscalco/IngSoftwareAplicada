import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PublisherDetailComponent } from './publisher-detail.component';

describe('Publisher Management Detail Component', () => {
  let comp: PublisherDetailComponent;
  let fixture: ComponentFixture<PublisherDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PublisherDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ publisher: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PublisherDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PublisherDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load publisher on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.publisher).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
