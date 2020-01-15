import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RadiobuttonsTestModule } from '../../../test.module';
import { ChooserComponent } from 'app/entities/chooser/chooser.component';
import { ChooserService } from 'app/entities/chooser/chooser.service';
import { Chooser } from 'app/shared/model/chooser.model';

describe('Component Tests', () => {
  describe('Chooser Management Component', () => {
    let comp: ChooserComponent;
    let fixture: ComponentFixture<ChooserComponent>;
    let service: ChooserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RadiobuttonsTestModule],
        declarations: [ChooserComponent],
        providers: []
      })
        .overrideTemplate(ChooserComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ChooserComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ChooserService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Chooser(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.choosers && comp.choosers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
