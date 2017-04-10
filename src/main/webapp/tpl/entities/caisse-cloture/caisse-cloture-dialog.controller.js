(function() {
    'use strict';

    angular
        .module('app')
        .controller('CaisseClotureDialogController', CaisseClotureDialogController);

    CaisseClotureDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'CaisseCloture','Caisse'];

    function CaisseClotureDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, CaisseCloture ,Caisse) {
        var vm = this;

        vm.caisseCloture = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.caisses = Caisse.query();

      

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.caisseCloture.id !== null) {
                CaisseCloture.update(vm.caisseCloture, onSaveSuccess, onSaveError);
            } else {
                CaisseCloture.save(vm.caisseCloture, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:caisseClotureUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


         vm.datePickerOpenStatus.dateDebut = false;
 vm.datePickerOpenStatus.dateFin = false;

        
         function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }

    }
})();
