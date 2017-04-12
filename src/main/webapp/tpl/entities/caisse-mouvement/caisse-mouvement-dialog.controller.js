(function() {
    'use strict';

    angular
        .module('app')
        .controller('CaisseMouvementDialogController', CaisseMouvementDialogController);

    CaisseMouvementDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'CaisseMouvement','Caisse'];

    function CaisseMouvementDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, CaisseMouvement ,Caisse) {
        var vm = this;

        vm.caisseMouvement = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
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
            if (vm.caisseMouvement.id !== null) {
                CaisseMouvement.update(vm.caisseMouvement, onSaveSuccess, onSaveError);
            } else {
                CaisseMouvement.save(vm.caisseMouvement, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:caisseMouvementUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


         vm.datePickerOpenStatus.dateMouvement = false;

        
         function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }

    }
})();
