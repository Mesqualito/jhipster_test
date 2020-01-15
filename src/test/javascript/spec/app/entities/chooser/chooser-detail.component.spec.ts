import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RadiobuttonsTestModule } from '../../../test.module';
import { ChooserDetailComponent } from 'app/entities/chooser/chooser-detail.component';
import { Chooser } from 'app/shared/model/chooser.model';

describe('Component Tests', () => {
  describe('Chooser Management Detail Component', () => {
    let comp: ChooserDetailComponent;
    let fixture: ComponentFixture<ChooserDetailComponent>;
    const route = ({ data: of({ chooser: new Chooser(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RadiobuttonsTestModule],
        declarations: [ChooserDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ChooserDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ChooserDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load chooser on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.chooser).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
